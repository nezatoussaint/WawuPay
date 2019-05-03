package com.hviewtech.wawupay.ui.activity.ticket

import android.content.Intent
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.local.Traveller
import com.hviewtech.wawupay.bean.remote.transport.SiteInfo
import com.hviewtech.wawupay.bean.remote.transport.TicketInfo
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.common.PayContract
import com.hviewtech.wawupay.contract.transport.TicketBookingContract
import com.hviewtech.wawupay.presenter.common.PayPresenter
import com.hviewtech.wawupay.presenter.transport.TicketBookingPresenter
import com.hviewtech.wawupay.ui.adapter.transportation.TravellerAdapter
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import kotlinx.android.synthetic.main.act_ticket_booking.*
import kotlinx.android.synthetic.main.dialog_pay.view.*
import me.shaohui.bottomdialog.BottomDialog
import javax.inject.Inject

class TicketBookingActivity : BaseMvpActivity(), TicketBookingContract.View, PayContract.View {
  companion object {
    const val RC_ADD_TRAVELLER = 1
    const val SITE_INFO = "siteinfo"
  }

  @Inject
  lateinit var mPresenter: TicketBookingPresenter
  @Inject
  lateinit var mPayPresenter: PayPresenter


  private var siteInfo: SiteInfo? = null
    get() = intent.getSerializableExtra(SITE_INFO) as SiteInfo?


  private lateinit var travellerAdapter: TravellerAdapter

  override fun getLayoutId(): Int {

    return R.layout.act_ticket_booking
  }

  override fun initialize() {
    super.initialize()

    val info = siteInfo

    departure.value = info?.route_name
    destination.value = info?.destination_name
    date.value = info?.schedule_time
    startTime.value = info?.start
    companyName.value = info?.company_name

    travellerAdapter = TravellerAdapter(mContext)
    recyclerView.setAdapters(mContext, listOf(travellerAdapter))

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode != RESULT_OK) {
      return
    }


    if (requestCode == RC_ADD_TRAVELLER) {

      val traveller: Traveller? = data?.getSerializableExtra(AddTravellerActivity.TRAVELLER) as Traveller?
      travellerAdapter.addData(traveller)


      if (traveller != null) {


        price.value = "${siteInfo?.price} Rwf"


      }


    }

  }


  fun addTraveller(view: View) {

    if (travellerAdapter.itemCount != 0) {
      return
    }

    goActivity(AddTravellerActivity::class, RC_ADD_TRAVELLER)
  }


  fun confirmAndPay(view: View) {

    if (travellerAdapter.itemCount == 0) {
      toast("Choose a traveller")
      return
    }

    val traveller = travellerAdapter.getItem(0)

    val info = siteInfo

    val name = "${traveller.firstName} ${traveller.lastName}"
    val phone = traveller.phoneNumber ?: ""
    val departureId = info?.id ?: ""
    val travelTime = info?.start ?: ""
    val travellDate = info?.schedule_time ?: ""
    mPresenter.orderTicket(name, phone, "", 0, departureId, 1, travelTime, travellDate)


  }


  override fun showOrderResult(data: TicketInfo?) {
    data ?: return

    val payDialog = BottomDialog()
    payDialog.setFragmentManager(supportFragmentManager)
      .setDimAmount(0.7f)
      .setLayoutRes(R.layout.dialog_pay)
      .setViewListener {

        it.totalAmount.value = price.value

        it.pay.setOnClickListener {
          payDialog.dismiss()
          PaymentEntranceFragment().title("Ticket Booking").show(supportFragmentManager, {
            val price = data.price ?: 0
            val ticketId = data.ticketId ?: ""
            mPayPresenter.postGetAPIMerPrepayId(
              Constants.ItemType.BUS,
              NumberUtils.valueOf(price * travellerAdapter.itemCount),
              it,
              ticketId
            )
          })

        }

      }.show()
  }

  override fun showPaymentSuccess() {
    toast("Payment Completed!")
    goActivity(TicketOrdersActivity::class)
  }
}