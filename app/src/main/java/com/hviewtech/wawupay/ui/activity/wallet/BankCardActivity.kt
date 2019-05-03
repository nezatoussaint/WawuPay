package com.hviewtech.wawupay.ui.activity.wallet

import android.os.Bundle
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.ui.fragment.wallet.BankCardFragment
import com.hviewtech.wawupay.ui.fragment.wallet.BankManageFragment
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/22
 * @description
 */

class BankCardActivity : BaseMvpActivity() {


  @Inject
  lateinit var mBankCardFragment: BankCardFragment
  @Inject
  lateinit var mBankManageFragment: BankManageFragment


  override fun getLayoutId(): Int {
    return R.layout.act_bank_card
  }

  public override fun initialize() {

    showCardListFragment()
  }

  fun showManageFragment(card: BankCard?) {
    val args = Bundle()
    args.putSerializable(BankManageFragment.BANKCARD, card)
    mBankManageFragment.setArguments(args)
    supportFragmentManager.beginTransaction().replace(R.id.container, mBankManageFragment)
      .addToBackStack("BankManageFragment").commit()
  }

  fun showCardListFragment() {
    supportFragmentManager.beginTransaction().replace(R.id.container, mBankCardFragment)
      .commit()
  }
}
