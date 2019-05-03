package com.hviewtech.wawupay.contract.utility

import com.hviewtech.wawupay.bean.remote.utility.TopupCharge
import com.hviewtech.wawupay.contract.Contract

interface TopupContract {

  interface View : Contract.View {
    fun showChargeInfo(data: TopupCharge?)

  }

  interface Presenter : Contract.Presenter
}