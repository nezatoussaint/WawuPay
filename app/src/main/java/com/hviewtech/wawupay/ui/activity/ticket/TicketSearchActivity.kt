package com.hviewtech.wawupay.ui.activity.ticket

import android.content.Intent
import android.view.View
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.transport.Site
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.contract.transport.TicketSearchContract
import com.hviewtech.wawupay.presenter.transport.TicketSearchPresenter
import com.hviewtech.wawupay.ui.dialog.common.DatePickerFragment
import com.hviewtech.wawupay.ui.dialog.common.TimePickerFragment
import kotlinx.android.synthetic.main.act_ticket_search.*
import java.util.*
import javax.inject.Inject

class TicketSearchActivity : BaseMvpActivity(), TicketSearchContract.View {

  @Inject
  lateinit var mPresenter: TicketSearchPresenter
  val clockTimeFragment = TimePickerFragment()
  val dateTimeFragment = DatePickerFragment()

  override fun getLayoutId(): Int {
    return R.layout.act_ticket_search
  }


  override fun initialize() {
    super.initialize()


    //    fromSite.itemsData = list

    //    toSite.itemsData = list
    val instance = Calendar.getInstance()
    dateTime.value =
        String.format(
          Locale.getDefault(),
          "%04d-%02d-%02d",
          instance.get(Calendar.YEAR),
          instance.get(Calendar.MONTH) + 1,
          instance.get(Calendar.DAY_OF_MONTH)
        )
    clockTime.value = String.format(
      Locale.getDefault(),
      "%02d:%02d",
      instance.get(Calendar.HOUR),
      instance.get(Calendar.MINUTE)
    )

    mPresenter.getDepartures(false)

    fromSite.onEmptyClicked = {
      mPresenter.getDepartures()
    }


    fromSite.onItemClicked = { index, data ->
      mPresenter.getDestinations(false, data.siteId)
    }



    clockTimeFragment.setCallback(object : TimePickerFragment.Callback {
      override fun onCancelled() {

      }

      override fun onDateTimeRecurrenceSet(
        selectedDate: SelectedDate?,
        hourOfDay: Int,
        minute: Int,
        recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
        recurrenceRule: String?
      ) {
        clockTime.value = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
      }
    })


    dateTimeFragment.setCallback(object : DatePickerFragment.Callback {
      override fun onCancelled() {

      }

      override fun onDateTimeRecurrenceSet(
        selectedDate: SelectedDate,
        hourOfDay: Int,
        minute: Int,
        recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
        recurrenceRule: String?
      ) {
        val calendar = selectedDate.firstDate
        dateTime.value =
            String.format(
              Locale.getDefault(),
              "%04d-%02d-%02d",
              calendar.get(Calendar.YEAR),
              calendar.get(Calendar.MONTH) + 1,
              calendar.get(Calendar.DAY_OF_MONTH)
            )
      }
    })
  }


  fun chooseClockTime(view: View) {

    clockTimeFragment.show(supportFragmentManager)

  }

  fun chooseDateTime(view: View) {

    dateTimeFragment.show(supportFragmentManager)

  }


  fun showOrders(view: View) {

    goActivity(TicketOrdersActivity::class)

  }


  fun contactUs(view: View) {
    goActivity(ContactUsActivity::class)
  }


  fun searchTickets(view: View) {
    val departure = fromSite.curData?.siteName

    if (departure.isNullOrBlank()) {
      toast("Choose a departure location")
      return
    }
    val destination = toSite.curData?.siteName
    if (destination.isNullOrBlank()) {
      toast("Choose a destination location")
      return
    }

    if (destination == departure) {
      toast("Departure and Destination cannot be the same.")
      return
    }


    val travelTime = clockTime.value

    val travelDate = dateTime.value


    val intent = Intent(mContext, TicketsListActivity::class.java)
    intent.putExtra(TicketsListActivity.DEPARTURE, departure)
    intent.putExtra(TicketsListActivity.DESTINATION, destination)
    intent.putExtra(TicketsListActivity.TRAVEL_DATE, travelDate)
    intent.putExtra(TicketsListActivity.TRAVEL_TIME, travelTime)
    goIntent(intent)
  }


  override fun showDepartures(data: List<Site>?) {
    fromSite.itemsData = data

    val siteId = fromSite.curData?.siteId

    siteId ?: return
    mPresenter.getDestinations(false, siteId)
  }


  override fun showDestinations(data: List<Site>?) {
    toSite.itemsData = data
  }
}



