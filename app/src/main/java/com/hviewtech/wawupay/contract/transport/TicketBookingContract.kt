package com.hviewtech.wawupay.contract.transport

import com.hviewtech.wawupay.bean.remote.transport.TicketInfo
import com.hviewtech.wawupay.contract.Contract

interface TicketBookingContract {

  interface View : Contract.View {
    fun showOrderResult(data: TicketInfo?)
  }

  interface Presenter : Contract.Presenter
}