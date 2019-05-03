package com.hviewtech.wawupay.ui.activity.wallet

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.PayInfo
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.textfilter.PointLengthFilter
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.common.utils.AppUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.wallet.SetAmountContract
import com.hviewtech.wawupay.presenter.wallet.SetAmountPresenter
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import kotlinx.android.synthetic.main.act_set_amount.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/17
 * @description
 */

class SetAmountActivity : BaseMvpActivity(), SetAmountContract.View {
  private var mPayInfo: PayInfo? = null

  @Inject
  lateinit var mPresenter: SetAmountPresenter

  private var mPlatformFee: PlatformFee? = null

  override fun getLayoutId(): Int {
    return R.layout.act_set_amount
  }

  override fun initialize() {
    super.initialize()

    mPayInfo = intent.getSerializableExtra(PAYMENT_INFO) as? PayInfo
    if (mPayInfo != null) {
      val amount = mPayInfo!!.amount
      if (amount!!.toDouble() > 0) {
        this.amount.isEnabled = false
      }
      val text = NumberUtils.toPlainString(amount)
      this.amount.value = text
      ActivityUtils.setSelection(this.amount, text)
    } else {
      this.amount.requestFocus()
      //stateAlwaysVisible
      val filter = PointLengthFilter(2)
      AppUtils.setEditTextInhibitInputSpace(this.amount, filter)

    }

    mPlatformFee = HawkExt.platformFee
  }

  fun setAmount(view: View) {
    val text = this.amount.value
    if (TextUtils.isEmpty(text)) {
      showError("Incorrect amount")
      return
    }
    if (mPlatformFee == null) {
      return
    }
    val amount = NumberUtils.valueOf(text)
    val compareTo = amount.compareTo(mPlatformFee!!.transferMaxAmount)
    if (compareTo == 1) {
      showError("Beyond max amount of transfer")
      return
    }
    PaymentEntranceFragment().title("Payment").show(supportFragmentManager, {

      val unit = {
        val intent = intent
        intent.putExtra(AMOUNT, NumberUtils.valueOf(text))
        setResult(Activity.RESULT_OK, intent)
        finish()
      }

      if (mPayInfo != null) {
        mPresenter.qrPayment(mPayInfo!!, NumberUtils.valueOf(text), it)
      } else {
        unit.invoke()
      }
    })
  }

  override fun showPaymentSuccess() {
    //ToastUtils.show("Payment success!");
    setResult(Activity.RESULT_OK)
    finish()
  }

  companion object {

    val AMOUNT = "amount"

    val PAYMENT_INFO = "payment info"
  }
}
