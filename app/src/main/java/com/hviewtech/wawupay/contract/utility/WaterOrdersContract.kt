package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.WaterOrders
import com.hviewtech.wawupay.contract.Contract

interface WaterOrdersContract {

  interface View : Contract.View {
    fun showOrders(success: Boolean = false, data: WaterOrders? = null)

  }

  interface Presenter : Contract.Presenter
}