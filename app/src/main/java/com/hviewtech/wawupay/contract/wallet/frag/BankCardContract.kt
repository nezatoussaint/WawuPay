package com.hviewtech.wawupay.contract.wallet.frag


import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/23
 * @description
 */

interface BankCardContract {
  interface View : Contract.View {

    fun showMyBankList(success: Boolean = true, bankCardList: List<BankCard>?)
  }

  interface Presenter : Contract.Presenter
}
