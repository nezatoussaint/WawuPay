package com.hviewtech.wawupay.ui.activity.electricity

import android.support.v7.widget.LinearLayoutManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.utility.ElectricityOrders
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.utility.ElectricityOrdersContract
import com.hviewtech.wawupay.presenter.utility.ElectricityOrdersPresenter
import com.hviewtech.wawupay.ui.adapter.utitlities.ElectricityOrdersAdapter
import kotlinx.android.synthetic.main.act_electricity_orders.*
import javax.inject.Inject

class ElectricityOrdersActivity : BaseMvpActivity(), ElectricityOrdersContract.View {

  @Inject
  lateinit var mPresenter: ElectricityOrdersPresenter

  lateinit var ordersAdapter: ElectricityOrdersAdapter

  var page = 1


  override fun getLayoutId(): Int {
    return R.layout.act_electricity_orders
  }

  override fun initialize() {
    super.initialize()

    refreshLayout.setOnRefreshListener {
      page = 1
      mPresenter.getElectricityOrders(page)
      refreshLayout.setNoMoreData(false)
      it.finishRefresh()
    }

    refreshLayout.setOnLoadMoreListener {
      page++
      mPresenter.getElectricityOrders(page)
      it.finishLoadMore()
    }


    recyclerView.layoutManager = LinearLayoutManager(mContext)

    ordersAdapter = ElectricityOrdersAdapter(mContext)
    recyclerView.setAdapters(mContext, listOf(ordersAdapter))

    mPresenter.getElectricityOrders(page, true)
  }


  override fun showOrders(success: Boolean, data: ElectricityOrders?) {
    if (page == 1) {
      refreshLayout.finishRefresh(success)
    } else {
      refreshLayout.finishLoadMore(success)
    }

    data ?: return

    val datas = data.data

    if (datas?.size ?: 0 < 10) {
      refreshLayout.setNoMoreData(true)
    }

    ordersAdapter.setDatas(datas)
  }
}