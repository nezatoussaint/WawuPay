package com.hviewtech.wawupay.ui.activity.electricity

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.utility.ElectricityCharge
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.ext.valueKeepState
import com.hviewtech.wawupay.common.textfilter.PointLengthFilter
import com.hviewtech.wawupay.common.utils.AppUtils
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.common.PayContract
import com.hviewtech.wawupay.contract.utility.ElectricityContract
import com.hviewtech.wawupay.presenter.common.PayPresenter
import com.hviewtech.wawupay.presenter.utility.ElectricityPresenter
import com.hviewtech.wawupay.ui.activity.ticket.ContactUsActivity
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import com.hviewtech.wawupay.ui.widget.RadioRecycleAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.act_electricity.*
import kotlinx.android.synthetic.main.dialog_pay_electricity.view.*
import me.shaohui.bottomdialog.BottomDialog
import java.math.BigDecimal
import javax.inject.Inject

class ElectricityActivity : BaseMvpActivity(), ElectricityContract.View, PayContract.View {


  @Inject
  lateinit var mPresenter: ElectricityPresenter
  @Inject
  lateinit var mPayPresenter: PayPresenter
  private var amount: BigDecimal = BigDecimal.ZERO
  private var meterNumber: String = ""

  override fun getLayoutId(): Int {
    return R.layout.act_electricity
  }

  override fun initialize() {
    super.initialize()


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


  fun showOrders(view: View) {

    goActivity(ElectricityOrdersActivity::class)

  }


  fun contactUs(view: View) {
    goActivity(ContactUsActivity::class)
  }


  fun pay(view: View) {

    val meterNumber = etMeterNumber.value
    if (meterNumber.isNullOrBlank()) {
      toast("Enter poc")
      return
    }

    amount = NumberUtils.valueOf(etAmount.value)

    if (amount == BigDecimal.ZERO) {
      toast("Enter amount")
      return
    }

    mPresenter.createElectricityOffer(amount, meterNumber)

  }

  override fun showOfferResult(data: ElectricityCharge?) {
    data ?: return

    val info = data.accept

    info ?: return
    meterNumber = info.meterNumber

    val payDialog = BottomDialog()
    payDialog.setFragmentManager(supportFragmentManager)
      .setDimAmount(0.7f)
      .setLayoutRes(R.layout.dialog_pay_electricity)
      .setViewListener {
        it.meterHolder.value = info.meterHolder
        it.meterNumber.value = info.meterNumber
        it.orderNo.value = info.orderNo
        it.amount.value = "${NumberUtils.toPlainString(info.amount)} Rwf"
        it.kwh.value = "${info.kwt} Kwh"


        it.pay.setOnClickListener {
          payDialog.dismiss()
          mPresenter.createElectricityOrder(info.amount, info.meterNumber, info.meterHolder, info.orderNo, info.kwt)
        }

      }.show()

  }

  override fun showCreateOrder(success: Boolean) {
    if (success) {
      PaymentEntranceFragment().title("Topup").show(supportFragmentManager, {
        mPayPresenter.postGetAPIMerPrepayId(
          Constants.ItemType.WATER,
          NumberUtils.valueOf(amount),
          it,
          meterNumber
        )
      })
    }
  }

  override fun showPaymentSuccess() {
    toast("Payment Success")
  }


}