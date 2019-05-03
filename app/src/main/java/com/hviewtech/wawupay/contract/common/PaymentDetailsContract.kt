package com.hviewtech.wawupay.contract.common


import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import com.hviewtech.wawupay.contract.Contract

interface PaymentDetailsContract {
  interface View : Contract.View {

    fun showPaymentDetails(success:Boolean = false, result: PaymentDetails? = null)

  }

  interface Presenter : Contract.Presenter
}
