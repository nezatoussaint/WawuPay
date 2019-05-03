package com.hviewtech.wawupay.presenter.transport

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.transport.Order
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.transport.TicketOrdersContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpDataTransformer
import com.hviewtech.wawupay.model.PayModel
import com.hviewtech.wawupay.model.TransportModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class TicketOrdersPresenter @Inject constructor(
  private val mTicketModel: TransportModel,
  private val mPayModel: PayModel
) :
  BasePresenter<TicketOrdersContract.View>(), TicketOrdersContract.Presenter {


  fun orderTicket(page: Int, showloading: Boolean = false) {
    val userId = HawkExt.info?.userId
    userId ?: return

    mTicketModel.queryOrdersRecord(10, page, userId)
      .bindToLifecycle(this)
      .compose(HttpDataTransformer())
      .subscribe(object : SimpleSubscriber<List<Order>?>(if (showloading) mView else null) {
        override fun onNext(data: List<Order>?) {
          super.onNext(data)
          mView?.showOrders(true, data)

        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showOrders(false)

        }

      })

  }


}