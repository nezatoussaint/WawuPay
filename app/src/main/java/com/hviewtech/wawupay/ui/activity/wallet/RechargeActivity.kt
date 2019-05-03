package com.hviewtech.wawupay.ui.activity.wallet

import android.text.TextUtils
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.RechargeContract
import com.hviewtech.wawupay.presenter.wallet.RechargePresenter
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import kotlinx.android.synthetic.main.act_recharge.*
import java.util.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/19
 * @description
 */

class RechargeActivity : BaseMvpActivity(), RechargeContract.View {


  @Inject
  lateinit var mPresenter: RechargePresenter

  private var whichBankCard = 0
  private val mBankItems = ArrayList<String>()
  private val mBankCardList = ArrayList<BankCard>()
  private var mPlatformFee: PlatformFee? = null

  override fun getLayoutId(): Int {
    return R.layout.act_recharge
  }

  override fun initialize() {
    mPlatformFee = HawkExt.platformFee

  }

  override fun onResume() {
    super.onResume()
    mPresenter.getBankCardList()
  }

  fun showBankList(view: View) {
    if (mBankCardList.size == 0) {
      showError("No bankcards")
      return
    }

    MaterialDialog.Builder(this)
      .items(mBankItems)
      .autoDismiss(true)
      .itemsCallbackSingleChoice(
        whichBankCard
      ) { dialog, _, which, text ->
        whichBankCard = which
        bankcard.value = mBankItems[which]
        true // allow selection
      }.show()
  }

  fun goNext(view: View) {
    val text = amount.value
    if (TextUtils.isEmpty(text)) {
      showError("Incorrect amount")
      return
    }
    if (mPlatformFee == null) {
      return
    }
    val amount = NumberUtils.valueOf(text)
    val compareTo = amount.compareTo(mPlatformFee!!.userRechargeMaxAmount)
    if (compareTo == 1) {
      showError("Beyond max amount of recharge")
      return
    }
    PaymentEntranceFragment().title("Payment").show(supportFragmentManager, {
      val bankId = mBankCardList[whichBankCard].bankCardId.toString()
      mPresenter.rechargeByBankcard(bankId, NumberUtils.valueOf(text))
    })
  }

  override fun rechargeSuccess() {
    toast("Recharge successful")
    amount.value = null
    finish()
  }

  override fun showMyBankList(success: Boolean, bankCardList: List<BankCard>?) {
    if (bankCardList == null) {
      return
    }
    mBankCardList.clear()
    mBankCardList.addAll(bankCardList)
    mBankItems.clear()
    for ((_, bankName, _, bankCardId) in bankCardList) {
      mBankItems.add("$bankName($bankCardId)")
    }
    if (mBankItems.size > 0) {
      bankcard.value = mBankItems[0]
    }
  }

}
