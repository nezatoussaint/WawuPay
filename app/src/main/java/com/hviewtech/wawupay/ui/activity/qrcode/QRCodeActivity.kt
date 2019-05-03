package com.hviewtech.wawupay.ui.activity.qrcode

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.webkit.URLUtil
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.RC_OPEN_GALLERY
import com.hviewtech.wawupay.RC_PERMISSION_CAMERA
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.wallet.PayInfo
import com.hviewtech.wawupay.common.action.CancelSwipeBack
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.contract.qrcode.QRCodeContract
import com.hviewtech.wawupay.data.http.transformer.SimpleTransformer
import com.hviewtech.wawupay.presenter.qrcode.QRCodePresenter
import com.hviewtech.wawupay.ui.activity.common.TxtActivity
import com.hviewtech.wawupay.ui.activity.wallet.SetAmountActivity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_qrcode.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class QRCodeActivity : BaseMvpActivity(), QRCodeContract.View, QRCodeView.Delegate, CancelSwipeBack {

  companion object {
    const val RESULT = "result"
  }


  @Inject
  lateinit var mPresenter: QRCodePresenter

  override fun getLayoutId(): Int {
    return R.layout.act_qrcode
  }

  override fun initialize() {
    super.initialize()

    qrcodeView.setDelegate(this)

    qrcodeView.setType(BarcodeType.ONLY_QR_CODE, null)


  }

  override fun onStart() {
    super.onStart()
    startCamera()
  }

  override fun onStop() {
    stopCamera()
    super.onStop()
  }

  override fun onDestroy() {
    qrcodeView.onDestroy()
    super.onDestroy()
  }


  @AfterPermissionGranted(RC_PERMISSION_CAMERA)
  fun startCamera() {
    val perms = arrayOf<String>(Manifest.permission.CAMERA)

    judgePermissions({
      qrcodeView.startCamera()
      qrcodeView.startSpotAndShowRect()
    }, RC_PERMISSION_CAMERA, *perms)
  }

  fun stopCamera() {
    val perms = arrayOf<String>(Manifest.permission.CAMERA)
    if (EasyPermissions.hasPermissions(this, *perms)) {
      qrcodeView.stopCamera()
    }
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode != RESULT_OK) return

    if (requestCode == RC_OPEN_GALLERY) {
      // 图片选择结果回调
      val selectList = PictureSelector.obtainMultipleResult(data)
      // 例如 LocalMedia 里面返回三种path
      // 1.media.getPath(); 为原图path
      // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
      // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
      // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
      if (selectList.size == 1) {
        val media = selectList[0]
        val path = media.path
        decode(path)
      }
    }

  }

  override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
    super.onPermissionsDenied(requestCode, perms)
    if (requestCode == RC_PERMISSION_CAMERA) {
      toast("相机打开失败!")
    }

  }

  fun openGallery(view: View) {
    openGallery()
  }

  private fun openGallery() {
    PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
      .selectionMode(PictureConfig.SINGLE)
      .previewImage(true)
      .isCamera(true)
      .imageFormat(PictureMimeType.PNG)
      .forResult(RC_OPEN_GALLERY)

  }

  override fun onScanQRCodeSuccess(result: String) {
    vibrate()
    handleQRCode(result)

  }

  override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    // 这里是通过修改提示文案来展示环境是否过暗的状态，也可以根据 isDark 的值来实现其他交互效果

    if (isDark) {
      qrcodeView.enableFlash()

    } else {
      qrcodeView.disableFlash()
    }
  }

  override fun onScanQRCodeOpenCameraError() {
    toast("相机打开失败!")
  }

  private fun vibrate() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      vibrator?.vibrate(VibrationEffect.createOneShot(100, 50))
    } else {
      vibrator?.vibrate(100)
    }
  }

  private fun handleQRCode(result: String) {
    if (URLUtil.isValidUrl(result)) {

      mPresenter.requestNet(result)

    } else {
      val intent = Intent(mContext, TxtActivity::class.java)
      intent.putExtra(TxtActivity.CONTENT, result)
      goIntent(intent)
    }

  }

  private fun decode(path: String) {
    Observable.create(object : ObservableOnSubscribe<String> {
      override fun subscribe(emitter: ObservableEmitter<String>) {
        val result = QRCodeDecoder.syncDecodeQRCode(path)
        emitter.onNext(result ?: "")
        emitter.onComplete()
      }
    })
      .bindToLifecycle(provider)
      .compose<String>(SimpleTransformer())
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result -> handleQRCode(result) }
  }


  override fun showPaymentInfo(payInfo: PayInfo?) {
    if (payInfo == null) {
      qrcodeView.startSpotDelay(500)
      return
    }
    val intent = Intent(mContext, SetAmountActivity::class.java)
    intent.putExtra(SetAmountActivity.PAYMENT_INFO, payInfo)
    goIntent(intent)
    finish()
  }

}
