package com.hviewtech.wawupay.ui.fragment.wallet

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpFragment
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.contract.wallet.frag.BankManageContract
import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.presenter.wallet.frag.BankManagePresenter
import com.hviewtech.wawupay.ui.activity.wallet.BankCardActivity
import kotlinx.android.synthetic.main.frag_bank_manage.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/23
 * @description
 */
@ActivityScope
class BankManageFragment @Inject constructor() : BaseMvpFragment(), BankManageContract.View {

  @Inject
  lateinit var mPresenter: BankManagePresenter

  private var mBankCard: BankCard? = null

  override fun getLayoutId(): Int {
    return R.layout.frag_bank_manage
  }


  public override fun initialize() {

    val arguments = arguments
    if (arguments != null) {

      mBankCard = arguments.getSerializable(BANKCARD) as? BankCard

      if (mBankCard != null) {


        bankName.value = mBankCard!!.bankName

        realName.value = mBankCard!!.realName

        cardNum.value = mBankCard!!.cardNo


      }

    }


    deleteBankcard.setOnClickListener {
      mBankCard ?: return@setOnClickListener
      mPresenter.delBankCard(mBankCard!!.bankCardId)
    }
  }


  override fun showDelBankcardSuccess() {
    val activity = activity
    if (activity != null && activity is BankCardActivity) {
      activity.showCardListFragment()
    }
  }

  companion object {

    val BANKCARD = "bankcard"
  }
}
