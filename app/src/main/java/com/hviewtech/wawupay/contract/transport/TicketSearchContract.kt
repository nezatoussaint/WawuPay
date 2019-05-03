package com.hviewtech.wawupay.contract.transport

import com.hviewtech.wawupay.bean.remote.transport.Site
import com.hviewtech.wawupay.contract.Contract

interface TicketSearchContract {

  interface View : Contract.View {
    fun showDepartures(data: List<Site>? = arrayListOf())
    fun showDestinations(data: List<Site>? = arrayListOf())

  }

  interface Presenter : Contract.Presenter
}