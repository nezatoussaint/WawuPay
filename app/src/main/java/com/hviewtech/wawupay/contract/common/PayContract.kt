package com.hviewtech.wawupay.contract.common


import com.hviewtech.wawupay.contract.Contract

interface PayContract {
  interface View : Contract.View {

    fun showPaymentSuccess()
  }

  interface Presenter : Contract.Presenter
}
