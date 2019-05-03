package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.WaterCharge
import com.hviewtech.wawupay.contract.Contract

interface WaterContract {

  interface View : Contract.View {
    fun showCreateOrder(success: Boolean)
    fun showOfferResult(data: WaterCharge?)

  }

  interface Presenter : Contract.Presenter
}