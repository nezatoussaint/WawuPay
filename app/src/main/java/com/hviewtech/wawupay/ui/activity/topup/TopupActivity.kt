package com.hviewtech.wawupay.ui.activity.topup

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.utility.TopupCharge
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.ext.valueKeepState
import com.hviewtech.wawupay.common.textfilter.PointLengthFilter
import com.hviewtech.wawupay.common.utils.AppUtils
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.common.PayContract
import com.hviewtech.wawupay.contract.utility.TopupContract
import com.hviewtech.wawupay.presenter.common.PayPresenter
import com.hviewtech.wawupay.presenter.utility.TopupPresenter
import com.hviewtech.wawupay.ui.activity.ticket.ContactUsActivity
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import com.hviewtech.wawupay.ui.widget.RadioRecycleAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.act_topup.*
import kotlinx.android.synthetic.main.dialog_pay_topup.view.*
import me.shaohui.bottomdialog.BottomDialog
import java.math.BigDecimal
import javax.inject.Inject

class TopupActivity : BaseMvpActivity(), TopupContract.View, PayContract.View {


  @Inject
  lateinit var mPresenter: TopupPresenter
  @Inject
  lateinit var mPayPresenter: PayPresenter

  private var amount: BigDecimal = BigDecimal.ZERO
  private var companyNameIndex: Int = -1

  override fun getLayoutId(): Int {
    return R.layout.act_topup
  }

  override fun initialize() {
    super.initialize()


    dvProvider.itemsData = arrayListOf("MTN", "TIGO")
    dvProvider.onItemClicked = { index, data ->
      companyNameIndex = index
    }

    val amounts = arrayListOf("5000", "10000", "2000", "15000", "1000", "25000")
    val adapter = RadioRecycleAdapter(mContext, amounts)

    adapter.setOnItemClickListener { holder, pos ->

      val value = amounts[pos]
      etAmount.valueKeepState = value
    }

    amountList.layoutManager = GridLayoutManager(mContext, 2)
    amountList.adapter = adapter


    val filter = PointLengthFilter(2)
    AppUtils.setEditTextInhibitInputSpace(etAmount, filter)

    RxTextView.textChanges(etAmount)
      .subscribe {
        adapter.setSelection(amounts.indexOf(it.toString()))
      }
  }

  fun pay(view: View) {
    if (companyNameIndex == -1) {
      toast("Choose service provider")
      return
    }

    val phoneNumber = etPhone.value
    if (phoneNumber.isNullOrBlank()) {
      toast("Enter phone number")
      return
    }
    amount = NumberUtils.valueOf(etAmount.value)

    if (amount == BigDecimal.ZERO) {
      toast("Enter amount")
      return
    }

    val payDialog = BottomDialog()
    payDialog.setFragmentManager(supportFragmentManager)
      .setDimAmount(0.7f)
      .setLayoutRes(R.layout.dialog_pay_topup)
      .setViewListener {

        it.tvProvider.value = this.dvProvider.curData
        it.tvAmount.value = this.etAmount.value

        it.tvPhone.value = this.etPhone.value
        it.pay.setOnClickListener {
          payDialog.dismiss()
          mPresenter.createTopupOrder(phoneNumber, companyNameIndex.toString(), amount)
        }

      }.show()

  }


  fun showOrders(view: View) {

    goActivity(TopupOrdersActivity::class)

  }


  fun contactUs(view: View) {
    goActivity(ContactUsActivity::class)
  }


  override fun showChargeInfo(data: TopupCharge?) {

    data ?: return
    val meterNumber = data.data?.meterNumber
    meterNumber ?: return

    PaymentEntranceFragment().title("Topup").show(supportFragmentManager, {
      mPayPresenter.postGetAPIMerPrepayId(
        Constants.ItemType.TOPUP,
        NumberUtils.valueOf(amount),
        it,
        meterNumber
      )
    })
  }

  override fun showPaymentSuccess() {
    toast("Payment success")
  }
}