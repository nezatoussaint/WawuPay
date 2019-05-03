package com.hviewtech.wawupay.contract

import com.hviewtech.wawupay.bean.remote.wallet.BankCard

interface RechargeContract {
  interface View : Contract.View {

    fun rechargeSuccess()

    fun showMyBankList(success: Boolean = false, bankCardList: List<BankCard>? = null)
  }

  interface Presenter : Contract.Presenter
}