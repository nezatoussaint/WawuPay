package com.hviewtech.wawupay.contract.transport

import com.hviewtech.wawupay.bean.remote.transport.SiteInfo
import com.hviewtech.wawupay.contract.Contract

interface TicketsListContract {

  interface View : Contract.View {
    fun showTickets(success: Boolean = false, datas: List<SiteInfo>? = arrayListOf())
  }

  interface Presenter : Contract.Presenter
}