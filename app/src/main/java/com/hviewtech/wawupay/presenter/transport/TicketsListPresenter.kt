package com.hviewtech.wawupay.presenter.transport

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.SiteInfo
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.transport.TicketsListContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpDataTransformer
import com.hviewtech.wawupay.model.TransportModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class TicketsListPresenter @Inject constructor(private val mTicketModel: TransportModel) :
  BasePresenter<TicketsListContract.View>(), TicketsListContract.Presenter {


  fun queryTicketsList(
    departure: String, destination: String, travelDate: String,
    travelTime: String, showloading: Boolean = false
  ) {

    val authToken = HawkExt.authToken

    authToken ?: return
    mTicketModel.queryTickets(departure, destination, travelDate, travelTime, authToken)
      .bindToLifecycle(this)
      .compose(HttpDataTransformer())
      .subscribe(object : SimpleSubscriber<List<SiteInfo>?>(if (showloading) mView else null) {
        override fun onNext(data: List<SiteInfo>?) {
          super.onNext(data)

          mView?.showTickets(true, data)

        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showTickets(false, null)
        }

      })

  }

}