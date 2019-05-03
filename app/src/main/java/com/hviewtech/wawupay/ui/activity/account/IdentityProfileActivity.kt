package com.hviewtech.wawupay.ui.activity.account

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.common.bindings.ImageBindings
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.ResourceUtils
import com.hviewtech.wawupay.contract.account.IdentityProfileContract
import com.hviewtech.wawupay.presenter.account.IdentityProfilePresenter
import com.hviewtech.wawupay.ui.activity.home.MainActivity
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.act_identity_profile.*
import javax.inject.Inject

class IdentityProfileActivity : BaseMvpActivity(), IdentityProfileContract.View {

  @Inject
  lateinit var mPresenter: IdentityProfilePresenter

  companion object {
    private val RC_ADD_FRONT = 1
    private val RC_ADD_BACK = 2
  }

  private var frontPicUrl: String? = null
  private var backPicUrl: String? = null

  override fun getLayoutId(): Int {
    return R.layout.act_identity_profile
  }

  override fun initialize() {
    super.initialize()
    RxRadioGroup.checkedChanges(type)
      .subscribe {
        if (it == R.id.idcard) {
          id.hint = "ID number"
          ImageBindings.setImageUri(frontHolder, defaultRes = R.drawable.idcard_front)
          ImageBindings.setImageUri(backHolder, defaultRes = R.drawable.idcard_back)
        } else if (it == R.id.passport) {
          id.hint = "Passport number"
          ImageBindings.setImageUri(frontHolder, defaultRes = R.drawable.passport_front)
          ImageBindings.setImageUri(backHolder, defaultRes = R.drawable.passport_back)
        }
      }
  }


  protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      when (requestCode) {
        RC_ADD_FRONT, RC_ADD_BACK -> {
          // 图片选择结果回调
          val selectList = PictureSelector.obtainMultipleResult(data)
          // 例如 LocalMedia 里面返回三种path
          // 1.media.getPath(); 为原图path
          // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
          // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
          // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
          if (selectList.size == 1) {
            val media = selectList[0]
            val path: String
            if (media.isCompressed) {
              path = media.compressPath
            } else {
              path = media.cutPath
            }
            val uri = ResourceUtils.parseFile2Uri(path)
            if (requestCode == RC_ADD_FRONT) {
              ImageBindings.setImageUri(frontPic, uri)
              mPresenter.uploadFrontPic(uri)
            } else {
              ImageBindings.setImageUri(backPic, uri)
              mPresenter.uploadBackPic(uri)
            }
          }
        }
      }
    }
  }

  fun addFrontPic(view: View) {
    showPicChooser(RC_ADD_FRONT)
  }


  fun addBackPic(view: View) {
    showPicChooser(RC_ADD_BACK)
  }

  fun ignore(view: View) {
    mPresenter.loginByToken()
  }


  fun submitIdentity(view: View) {

    val idType: Int

    val checkedRadioButtonId = type.checkedRadioButtonId
    if (checkedRadioButtonId == R.id.passport) {
      idType = 2
    } else {
      idType = 1
    }
    val idNo = id.value
    if (TextUtils.isEmpty(idNo)) {
      if (idType == 1) {
        showError("Invalid ID number")
      } else {
        showError("Invalid passport number")
      }
      return
    }
    val realName = realName.value
    if (TextUtils.isEmpty(realName)) {
      showError("Invalid real name")
      return
    }
    if (frontPicUrl.isNullOrBlank() || backPicUrl.isNullOrBlank()) {
      showError("ID photo incomplete")
      return
    }
    mPresenter.uploadCertificationAndLogin(idType, idNo, realName, frontPicUrl, backPicUrl)
  }


  override fun showUploadFrontPicSuccess(url: String?) {
    frontPicUrl = url
  }

  override fun showUploadBackPicSuccess(url: String?) {
    backPicUrl = url
  }

  override fun showLoginSuccess(result: Login?) {
    HawkExt.saveInfo(result)
    goActivity(MainActivity::class)
  }


  private fun showPicChooser(requestCode: Int) {
    PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
      .selectionMode(PictureConfig.SINGLE)
      .previewImage(true)
      .isCamera(true)
      .imageFormat(PictureMimeType.PNG)
      .compress(true)
      .minimumCompressSize(100)
      .withAspectRatio(7, 5)
      .enableCrop(true)
      .showCropFrame(true)
      .showCropGrid(true)
      .rotateEnabled(true)
      .scaleEnabled(true)
      .freeStyleCropEnabled(true)
      .forResult(requestCode)
  }

}