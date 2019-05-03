package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.ElectricityOrders
import com.hviewtech.wawupay.contract.Contract

interface ElectricityOrdersContract {

  interface View : Contract.View {
    fun showOrders(success: Boolean = false, data: ElectricityOrders? = null)

  }

  interface Presenter : Contract.Presenter
}