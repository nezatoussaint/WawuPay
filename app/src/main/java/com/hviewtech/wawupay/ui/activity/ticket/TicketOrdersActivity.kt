package com.hviewtech.wawupay.ui.activity.ticket

import android.support.v7.widget.LinearLayoutManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.transport.Order
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.transport.TicketOrdersContract
import com.hviewtech.wawupay.presenter.transport.TicketOrdersPresenter
import com.hviewtech.wawupay.ui.adapter.transportation.TicketOrdersAdapter
import kotlinx.android.synthetic.main.act_ticket_orders.*
import javax.inject.Inject

class TicketOrdersActivity : BaseMvpActivity(), TicketOrdersContract.View {


  @Inject
  lateinit var mPresenter: TicketOrdersPresenter

  lateinit var ordersAdapter: TicketOrdersAdapter

  var page = 1


  override fun getLayoutId(): Int {
    return R.layout.act_ticket_orders
  }

  override fun initialize() {
    super.initialize()

    refreshLayout.setOnRefreshListener {
      page = 1
      mPresenter.orderTicket(page)
      refreshLayout.setNoMoreData(false)
    }

    refreshLayout.setOnLoadMoreListener {
      page++
      mPresenter.orderTicket(page)
    }

    recyclerView.layoutManager = LinearLayoutManager(mContext)

    ordersAdapter = TicketOrdersAdapter(mContext)
    recyclerView.setAdapters(mContext, listOf(ordersAdapter))

    mPresenter.orderTicket(page, true)
  }


  override fun showOrders(success: Boolean, datas: List<Order>?) {
    if (page == 1) {
      refreshLayout.finishRefresh(success)
    } else {
      refreshLayout.finishLoadMore(success)
    }
    if (datas?.size?:0 < 10){
      refreshLayout.setNoMoreData(true)
    }

    ordersAdapter.setDatas(datas)
  }
}