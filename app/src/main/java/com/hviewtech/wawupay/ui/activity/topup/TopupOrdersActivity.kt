package com.hviewtech.wawupay.ui.activity.topup

import android.support.v7.widget.LinearLayoutManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.utility.TopupOrders
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.utility.TopupOrdersContract
import com.hviewtech.wawupay.presenter.utility.TopupOrdersPresenter
import com.hviewtech.wawupay.ui.adapter.utitlities.TopupOrdersAdapter
import kotlinx.android.synthetic.main.act_topup_orders.*
import javax.inject.Inject

class TopupOrdersActivity : BaseMvpActivity(), TopupOrdersContract.View {


  @Inject
  lateinit var mPresenter: TopupOrdersPresenter

  lateinit var ordersAdapter: TopupOrdersAdapter

  var page = 1


  override fun getLayoutId(): Int {
    return R.layout.act_topup_orders
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

    ordersAdapter = TopupOrdersAdapter(mContext)
    recyclerView.setAdapters(mContext, listOf(ordersAdapter))

    mPresenter.getTopupOrders(page, true)
  }


  override fun showOrders(success: Boolean, data: TopupOrders?) {
    if (page == 1) {
      refreshLayout.finishRefresh(success)
    } else {
      refreshLayout.finishLoadMore(success)
    }

    data ?: return



    if (data.data?.size ?: 0 < 10) {
      refreshLayout.setNoMoreData(true)
    }

    ordersAdapter.setDatas(data.data)
  }

}