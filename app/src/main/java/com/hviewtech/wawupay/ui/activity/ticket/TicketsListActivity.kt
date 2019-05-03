package com.hviewtech.wawupay.ui.activity.ticket

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.transport.SiteInfo
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.transport.TicketsListContract
import com.hviewtech.wawupay.presenter.transport.TicketsListPresenter
import com.hviewtech.wawupay.ui.adapter.transportation.TicketsListAdapter
import kotlinx.android.synthetic.main.act_tickets_list.*
import javax.inject.Inject

class TicketsListActivity : BaseMvpActivity(), TicketsListContract.View {

  companion object {
    const val DEPARTURE = "departure"
    const val DESTINATION = "destination"
    const val TRAVEL_DATE = "travelDate"
    const val TRAVEL_TIME = "travelTime"
  }

  @Inject
  lateinit var mPresenter: TicketsListPresenter

  lateinit var ticketsListAdapter: TicketsListAdapter

  private var departure: String = ""
    get() = intent.getStringExtra(DEPARTURE)

  private var destination: String = ""
    get() = intent.getStringExtra(DESTINATION)
  private var travelDate: String = ""

    get() = intent.getStringExtra(TRAVEL_DATE)

  private var travelTime: String = ""
    get() = intent.getStringExtra(TRAVEL_TIME)


  override fun getLayoutId(): Int {
    return R.layout.act_tickets_list
  }

  override fun initialize() {
    super.initialize()

    refreshLayout.setOnRefreshListener {
      mPresenter.queryTicketsList(departure, destination, travelDate, travelTime)
    }


    ticketsListAdapter = TicketsListAdapter(mContext)

    recyclerView.setAdapters(mContext, listOf(ticketsListAdapter))


    mPresenter.queryTicketsList(departure, destination, travelDate, travelTime, true)

  }


  override fun showTickets(success: Boolean, datas: List<SiteInfo>?) {
    ticketsListAdapter.setDatas(datas)

    refreshLayout.finishRefresh(success)
  }
}