package com.hviewtech.wawupay.ui.activity.profile

import android.graphics.Bitmap
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.common.app.TransformationManagers
import com.hviewtech.wawupay.common.bindings.ImageBindings
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.data.http.UserService
import com.hviewtech.wawupay.data.http.transformer.EmptyTransformer
import io.reactivex.Observable
import kotlinx.android.synthetic.main.act_personal_qrcode.*

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class PersonalQrcodeActivity : BaseActivity() {
  override fun getLayoutId(): Int {
    return R.layout.act_personal_qrcode
  }

  override fun initialize() {
    super.initialize()

    val info = HawkExt.info

    if (info != null) {
      ImageBindings.setImageUri(avatar, info.profilePic, transformation = TransformationManagers.cropCircle())

      nickname.value = info.nickname

      sex.setImageResource(if (info.sex == 0) R.drawable.icon_sex_woman else R.drawable.icon_sex_man)



      val qrcodeSize = resources.getDimensionPixelSize(R.dimen.dp_158)

      val num = info.num
      val url = String.format("%s/contact/qrPlatformUser/%s", UserService.REL_URL, num)

      Observable.create<Bitmap> {
        val bitmap = QRCodeEncoder.syncEncodeQRCode(url, qrcodeSize)
        if (bitmap != null) {
          it.onNext(bitmap)
        }
        it.onComplete()
      }
        .compose(EmptyTransformer())
        .subscribe { bitmap -> qrcode.setImageBitmap(bitmap) }
    }

  }
}
