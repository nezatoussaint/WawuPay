package com.hviewtech.wawupay.contract.wallet

import com.hviewtech.wawupay.bean.remote.wallet.Account
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/19
 * @description
 */

interface WithdrawDepositContract {
  interface View : Contract.View {

    fun updatePlatformFee(result: PlatformFee?)

    fun showWithdrawResult(result: Account?)

    fun showMyBankList(success:Boolean = false, bankCardList: List<BankCard>? = null)
  }

  interface Presenter : Contract.Presenter
}
