package com.hviewtech.wawupay.ui.activity.water

import android.support.v7.widget.LinearLayoutManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.utility.WaterOrders
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.utility.WaterOrdersContract
import com.hviewtech.wawupay.presenter.utility.WaterOrdersPresenter
import com.hviewtech.wawupay.ui.adapter.utitlities.WaterOrdersAdapter
import kotlinx.android.synthetic.main.act_water_orders.*
import javax.inject.Inject

class WaterOrdersActivity : BaseMvpActivity(), WaterOrdersContract.View {

  @Inject
  lateinit var mPresenter: WaterOrdersPresenter

  lateinit var ordersAdapter: WaterOrdersAdapter

  var page = 1


  override fun getLayoutId(): Int {
    return R.layout.act_water_orders
  }

  override fun initialize() {
    super.initialize()

    refreshLayout.setOnRefreshListener {
      page = 1
      mPresenter.getTopupOrders(page)
      refreshLayout.setNoMoreData(false)
      it.finishRefresh()
    }

    refreshLayout.setOnLoadMoreListener {
      page++
      mPresenter.getTopupOrders(page)
      it.finishLoadMore()
    }


    recyclerView.layoutManager = LinearLayoutManager(mContext)

    ordersAdapter = WaterOrdersAdapter(mContext)
    recyclerView.setAdapters(mContext, listOf(ordersAdapter))

    mPresenter.getTopupOrders(page, true)
  }


  override fun showOrders(success: Boolean, data: WaterOrders?) {
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