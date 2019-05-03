package com.hviewtech.wawupay.ui.activity.wallet

import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.common.ext.goActivity


class PersonalWalletActivity : BaseActivity() {

  override fun getLayoutId(): Int {
    return R.layout.act_personal_wallet
  }

  fun showPayment(view: View) {
    goActivity(PaymentActivity::class)
  }

  fun showBalance(view: View) {
    goActivity(BalanceActivity::class)
  }

  fun showBankCard(view: View) {
    goActivity(BankCardActivity::class)
  }
}
