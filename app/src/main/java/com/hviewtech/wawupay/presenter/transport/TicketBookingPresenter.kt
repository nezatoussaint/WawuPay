package com.hviewtech.wawupay.presenter.transport

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.TicketInfo
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.transport.TicketBookingContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTicketTransformer
import com.hviewtech.wawupay.model.TransportModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class TicketBookingPresenter @Inject constructor(
  private val mTicketModel: TransportModel
) :
  BasePresenter<TicketBookingContract.View>(), TicketBookingContract.Presenter {


  fun orderTicket(
    name: String,
    phone: String,
    email: String,
    gender: Int,
    destination_id: String,
    seat: Int,
    travel_time: String,
    travel_date: String
  ) {

    val authToken = HawkExt.authToken
    val userId = HawkExt.info?.userId
    authToken ?: return
    userId ?: return
    mTicketModel.orderTicket(
      authToken,
      name,
      phone,
      email,
      gender,
      destination_id,
      seat,
      travel_time,
      travel_date,
      userId
    )
      .bindToLifecycle(this)
      .compose(HttpTicketTransformer())
      .subscribe(object : SimpleSubscriber<TicketInfo?>(mView) {
        override fun onNext(data: TicketInfo?) {
          super.onNext(data)
          mView?.showOrderResult(data)

        }

        override fun onError(e: Throwable) {
          super.onError(e)
        }

      })

  }

}