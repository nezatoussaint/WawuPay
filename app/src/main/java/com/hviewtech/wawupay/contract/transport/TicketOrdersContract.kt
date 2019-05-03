package com.hviewtech.wawupay.contract.transport

import com.hviewtech.wawupay.bean.remote.transport.Order
import com.hviewtech.wawupay.contract.Contract

interface TicketOrdersContract {

  interface View : Contract.View {
    fun showOrders(success: Boolean = false, datas: List<Order>? = arrayListOf())

  }

  interface Presenter : Contract.Presenter
}