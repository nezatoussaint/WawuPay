package com.hviewtech.wawupay.presenter.transport

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.Site
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.transport.TicketSearchContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpDataTransformer
import com.hviewtech.wawupay.model.TransportModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class TicketSearchPresenter @Inject constructor(private val mTicketModel: TransportModel) :
  BasePresenter<TicketSearchContract.View>(), TicketSearchContract.Presenter {


  fun getDepartures(showloading: Boolean = true) {

    val authToken = HawkExt.authToken

    authToken ?: return
    mTicketModel.getDepartures(authToken)
      .bindToLifecycle(this)
      .compose(HttpDataTransformer())
      .subscribe(object : SimpleSubscriber<List<Site>?>(if (showloading) mView else null) {
        override fun onNext(data: List<Site>?) {
          super.onNext(data)

          mView?.showDepartures(data)

        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showDepartures()
        }
      })

  }

  fun getDestinations(showloading: Boolean = true, departureId: Int) {

    val authToken = HawkExt.authToken

    authToken ?: return
    mTicketModel.getDestinations(departureId, authToken)
      .bindToLifecycle(this)
      .compose(HttpDataTransformer())
      .subscribe(object : SimpleSubscriber<List<Site>?>(if (showloading) mView else null) {
        override fun onNext(data: List<Site>?) {
          super.onNext(data)

          mView?.showDestinations(data)

        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showDestinations()
        }

      })

  }

}