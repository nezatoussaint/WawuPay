package com.hviewtech.wawupay.presenter.transport

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.AuthInfo
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.transport.TicketSearchContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.TransportModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class AuthPresenter @Inject constructor(private val mTicketModel: TransportModel) :
  BasePresenter<TicketSearchContract.View>(), TicketSearchContract.Presenter {


  fun getAuthInfo(userName: String, password: String) {


    mTicketModel.getAuthInfo("wowe1", "d1")
      .bindToLifecycle(this)
      .compose<AuthInfo>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<AuthInfo?>() {
        override fun onNext(data: AuthInfo?) {
          super.onNext(data)

          HawkExt.authInfo = data
        }
      })
  }

}