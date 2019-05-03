package com.hviewtech.wawupay.presenter.wallet


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.PayInfo
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.wallet.SetAmountContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.PayModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author su
 * @date 2018/4/29
 * @description
 */

class SetAmountPresenter @Inject
constructor(private val mPayModel: PayModel) : BasePresenter<SetAmountContract.View>(), SetAmountContract.Presenter {

  fun qrPayment(payInfo: PayInfo, amount: BigDecimal, payPassword: String) {
    val accountId = HawkExt.info?.accountId
    accountId ?: return
    val paySign = payInfo.paySign
    paySign ?: return
    mPayModel.postQrPayment(paySign, amount, accountId, payPassword)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(mView) {
        override fun onNext(data: Any?) {
          mView?.showPaymentSuccess()

        }

      })
  }
}
