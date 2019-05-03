package com.hviewtech.wawupay.contract.wallet.frag


import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/23
 * @description
 */

interface BankManageContract {
  interface View : Contract.View {

    fun showDelBankcardSuccess()
  }

  interface Presenter : Contract.Presenter
}
