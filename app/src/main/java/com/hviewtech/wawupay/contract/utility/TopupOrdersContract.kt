package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.TopupOrders
import com.hviewtech.wawupay.contract.Contract

interface TopupOrdersContract {

  interface View : Contract.View {
    fun showOrders(success:Boolean = false, data: TopupOrders? = null)

  }

  interface Presenter : Contract.Presenter
}