package com.hviewtech.wawupay.presenter.wallet

import android.graphics.Bitmap
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.PrepayId
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.wallet.PaymentContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.data.http.transformer.SimpleTransformer
import com.hviewtech.wawupay.model.PayModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/17
 * @description
 */

class PaymentPresenter @Inject constructor(private val mPayModel: PayModel) : BasePresenter<PaymentContract.View>(),
  PaymentContract.Presenter {

  fun generateQRCode(content: String? = "", qrcodeSize: Int) {
    Observable.create<Bitmap> {
      val bitmap = QRCodeEncoder.syncEncodeQRCode(content, qrcodeSize)
      if (bitmap != null) {
        it.onNext(bitmap)
      }
      it.onComplete()
    }
      .bindToLifecycle(this)
      .compose(SimpleTransformer())
      .subscribe { bitmap -> mView?.updateQRCode(bitmap) }
  }

  fun generatePrepayUrl(showloading: Boolean = true, amount: BigDecimal) {
    val accountId = HawkExt.info?.accountId
    accountId ?: return
    mPayModel.postGetUserPrepayId(accountId, amount)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<PrepayId?>(if (showloading) mView else null) {
        override fun onNext(data: PrepayId?) {
          mView?.updateQRCode(data)
        }

      })
  }
}
