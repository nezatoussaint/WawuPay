package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.ElectricityCharge
import com.hviewtech.wawupay.contract.Contract

interface ElectricityContract {

  interface View : Contract.View {
    fun showCreateOrder(success: Boolean = false)
    fun showOfferResult(data: ElectricityCharge? = null)

  }

  interface Presenter : Contract.Presenter
}