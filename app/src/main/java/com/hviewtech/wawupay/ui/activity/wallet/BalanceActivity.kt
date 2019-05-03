package com.hviewtech.wawupay.ui.activity.wallet

import android.content.Intent
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.wallet.BalanceInfo
import com.hviewtech.wawupay.common.app.TransformationManagers
import com.hviewtech.wawupay.common.bindings.ImageBindings
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.wallet.BalanceContract
import com.hviewtech.wawupay.presenter.wallet.BalancePresenter
import kotlinx.android.synthetic.main.act_balance.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/18
 * @description
 */

class BalanceActivity : BaseMvpActivity(), BalanceContract.View {

  @Inject
  lateinit var mPresenter: BalancePresenter

  private var mBalanceInfo: BalanceInfo? = null


  override fun getLayoutId(): Int {
    return R.layout.act_balance
  }

  public override fun initialize() {
    val info = HawkExt.info
    if (info != null) {
      ImageBindings.setImageUri(avatar, info.profilePic, transformation = TransformationManagers.cropCircle())
    }
  }

  override fun onResume() {
    super.onResume()
    mPresenter.initBalanceInfo()

    val info = HawkExt.info
    if (info != null) {

    }
  }

  fun showRecharge(view: View) {

    goActivity(RechargeActivity::class)
  }

  fun showDetails(view: View) {
    goActivity(PaymentDetailsActivity::class)
  }

  fun showWithdrawDeposit(view: View) {
    if (mBalanceInfo == null) {
      mPresenter.initBalanceInfo()
      showError("Loading balance info...")
      return
    }
    val intent = Intent(mContext, WithdrawDepositActivity::class.java)
    intent.putExtra(WithdrawDepositActivity.BALANCE_INFO, mBalanceInfo)
    goIntent(intent)
  }

  override fun showBalanceInfo(result: BalanceInfo?) {
    mBalanceInfo = result
    val value = NumberUtils.toPlainString(result?.accountBalance)
    balance.value = value
  }

}
