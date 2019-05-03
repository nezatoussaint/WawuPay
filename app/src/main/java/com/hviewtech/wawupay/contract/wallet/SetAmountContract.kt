package com.hviewtech.wawupay.contract.wallet


import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/17
 * @description
 */

interface SetAmountContract {

  interface View : Contract.View {

    fun showPaymentSuccess()
  }

  interface Presenter : Contract.Presenter
}
