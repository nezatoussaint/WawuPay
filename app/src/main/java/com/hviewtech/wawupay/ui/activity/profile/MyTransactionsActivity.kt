package com.hviewtech.wawupay.ui.activity.profile

import android.view.View
import android.widget.AdapterView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.common.PaymentDetailsContract
import com.hviewtech.wawupay.presenter.common.PaymentDetailsPresenter
import com.hviewtech.wawupay.ui.adapter.profile.TransactionLogsAdapter
import kotlinx.android.synthetic.main.act_my_transactions.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/25
 * @description
 */

class MyTransactionsActivity : BaseMvpActivity(), PaymentDetailsContract.View {

  @Inject
  lateinit var mPresenter: PaymentDetailsPresenter

  private lateinit var categories: Array<String>
  private lateinit var mAdapter: TransactionLogsAdapter
  private var page = 1
  private var category = -1
  override fun getLayoutId(): Int {
    return R.layout.act_my_transactions
  }

  override fun initialize() {
    super.initialize()
    categories = resources.getStringArray(R.array.transaction_category)
    spinnerCategory.attachDataSource(categories.toList())
    spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var category = -1
        if (position > 0) {
          category = position
        }
        this@MyTransactionsActivity.category = category
        page = 1
        mPresenter.getPaymentDetails(category, page, true)
      }

    })

    refreshLayout.setOnRefreshListener {
      page = 1
      mPresenter.getPaymentDetails(category, page)

    }

    refreshLayout.setOnLoadMoreListener {
      page++
      mPresenter.getPaymentDetails(category, page)
    }

    mAdapter = TransactionLogsAdapter(mContext)

    recyclerView.setAdapters(mContext, listOf(mAdapter))

    // 初始化数据
    page = 1
    mPresenter.getPaymentDetails(category, page)

  }


  override fun showPaymentDetails(success: Boolean, result: PaymentDetails?) {
    if (result != null) {
      refreshLayout.setEnableLoadMore(result.pageCount > page)
    }

    if (page == 1) {
      refreshLayout.finishRefresh(success)
    } else {
      refreshLayout.finishLoadMore(success)
    }

    if (page == 1) {
      mAdapter.setDatas(result?.list)
    } else {
      mAdapter.addDatas(result?.list)
    }
  }
}
