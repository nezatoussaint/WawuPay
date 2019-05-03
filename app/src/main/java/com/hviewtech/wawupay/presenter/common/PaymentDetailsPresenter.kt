package com.hviewtech.wawupay.presenter.common

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.common.PaymentDetailsContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle

import javax.inject.Inject

class PaymentDetailsPresenter @Inject
constructor(private val mUserModel: UserModel) : BasePresenter<PaymentDetailsContract.View>(),
  PaymentDetailsContract.Presenter {

  fun getPaymentDetails(page: Int) {
    getPaymentDetails(-1, page)
  }

  fun getPaymentDetails(category: Int, page: Int, showloading: Boolean = false) {
    val userId = HawkExt.info?.userId
    userId ?: return

    mUserModel.postGetUserBillInfoList(userId, category, page)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<PaymentDetails?>(if (showloading) mView else null) {
        override fun onNext(data: PaymentDetails?) {
          mView?.showPaymentDetails(true, data)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showPaymentDetails()
        }
      })
  }
}
