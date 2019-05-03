package com.hviewtech.wawupay.ui.activity.wallet

import android.text.Html
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.Account
import com.hviewtech.wawupay.bean.remote.wallet.BalanceInfo
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.common.utils.VerificationUtils
import com.hviewtech.wawupay.contract.wallet.WithdrawDepositContract
import com.hviewtech.wawupay.presenter.wallet.WithdrawDepositPresenter
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.act_withdraw_deposit.*
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/19
 * @description
 */

class WithdrawDepositActivity : BaseMvpActivity(), WithdrawDepositContract.View {
  private var whichBankCard = 0
  private var mPlatformFee: PlatformFee? = null

  @Inject
  lateinit var mPresenter: WithdrawDepositPresenter

  private val mBankItems = ArrayList<String>()
  private val mBankCardList = ArrayList<BankCard>()
  private var mBalanceInfo: BalanceInfo? = null

  override fun getLayoutId(): Int {
    return R.layout.act_withdraw_deposit
  }

  public override fun initialize() {

    mBalanceInfo = intent.getSerializableExtra(BALANCE_INFO) as BalanceInfo
    if (mBalanceInfo != null) {
      totalAmount.text =
          Html.fromHtml(
            "The current balance of change " + NumberUtils.toPlainString(
              mBalanceInfo!!.enabledBalance
            ) + " RWF,<font color='#393939'>All Withdraw!</font>"
          )
    }

    mPlatformFee = HawkExt.platformFee
    if (mPlatformFee != null) {
      updatePlatformFee(mPlatformFee)
    }
    mPresenter.getPlatformFee()

    RxTextView.textChanges(amount)
      .subscribe {
        mPlatformFee ?: return@subscribe
        val value = it.toString()
        if (VerificationUtils.isNumber(value)) {
          // 计算用
          val amountDecimal = NumberUtils.valueOf(value)
          val tmpFeeDecimal = NumberUtils.multiply(amountDecimal, mPlatformFee!!.userWithdrawFee.toBigDecimal())
          val platformFeeDecimal = NumberUtils.divide(tmpFeeDecimal, BigDecimal.valueOf(100.0))
          // 得到结果
          val platformFee = NumberUtils.toPlainString(platformFeeDecimal)
          val finalAmount = NumberUtils.toPlainString(amountDecimal.subtract(platformFeeDecimal))
          // 赋值
          this.platformFee.value = platformFee
          this.finalAmount.value = finalAmount
        } else {
          this.platformFee.value = "0.00"
          this.finalAmount.value = "0.00"
        }
      }
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
      ) { dialog, view, which, text ->
        whichBankCard = which
        bankcard.setText(mBankItems[which])
        true // allow selection
      }.show()
  }

  fun goNext(view: View) {
    if (mPlatformFee == null) {
      return
    }
    val text = amount.value
    val amount = NumberUtils.valueOf(text)
    val compareTo = amount.compareTo(mPlatformFee!!.userWithdrawMaxAmount)
    if (compareTo == 1) {
      showError("Beyond max amount of withdraw")
      return
    }
    PaymentEntranceFragment().title("Payment").show(supportFragmentManager, {
      val bankId = mBankCardList[whichBankCard].bankCardId.toString()
      mPresenter.withdrawToBankCard(bankId, amount)

    })
  }

  fun allWithdraw(view: View) {
    if (mBalanceInfo == null) {
      return
    }

    val text = NumberUtils.toPlainString(mBalanceInfo!!.enabledBalance)
    amount.value = text
    ActivityUtils.setSelection(amount, text)

  }

  override fun updatePlatformFee(result: PlatformFee?) {
    result ?: return
    feeRate.value = NumberUtils.toPlainString(result.userWithdrawFee.toBigDecimal()) + "%"

  }

  override fun showWithdrawResult(result: Account?) {
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

  companion object {
    val BALANCE_INFO = "balance info"
  }

}
