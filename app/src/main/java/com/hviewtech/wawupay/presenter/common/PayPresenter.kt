package com.hviewtech.wawupay.presenter.common

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.MerPrepayId
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.common.PayContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.PayModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

class PayPresenter @Inject constructor(private val mPayModel: PayModel) : BasePresenter<PayContract.View>(),
  PayContract.Presenter {


  fun postGetAPIMerPrepayId(itemType: Int, amount: BigDecimal, payPassword: String, itemId: String) {
    mView?.showloading()
    mPayModel.postGetAPIMerPrepayId(itemType, amount, itemId)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<MerPrepayId?>() {
        override fun onNext(data: MerPrepayId?) {

          val paySign = data?.paySign
          val accountId = HawkExt.info?.accountId

          if (paySign.isNullOrBlank() || accountId.isNullOrBlank()) {
            mView?.dismissloading()
            mView?.showError("Payment error, retry please!")
            return
          }


          postPayment(paySign, amount, accountId, payPassword)


        }

        override fun onError(e: Throwable) {
          mView?.dismissloading()
        }
      })
  }


  fun postPayment(
    paySign: String,
    amount: BigDecimal,
    accountId: String,
    payPassword: String
  ) {

    mPayModel.postQrPayment(paySign, amount, accountId, payPassword)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          super.onNext(data)
          mView?.dismissloading()
          mView?.showPaymentSuccess()
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.dismissloading()
        }
      })

  }
}