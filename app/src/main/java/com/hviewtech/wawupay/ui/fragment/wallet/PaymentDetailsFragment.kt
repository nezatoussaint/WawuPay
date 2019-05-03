package com.hviewtech.wawupay.ui.fragment.wallet

import com.alibaba.android.vlayout.DelegateAdapter
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpFragment
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.common.PaymentDetailsContract
import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.presenter.common.PaymentDetailsPresenter
import com.hviewtech.wawupay.ui.activity.wallet.PaymentDetailsActivity
import com.hviewtech.wawupay.ui.adapter.wallet.PaymentDetailsAdapter
import kotlinx.android.synthetic.main.frag_payment_details.*
import java.util.*
import javax.inject.Inject

@ActivityScope
class PaymentDetailsFragment @Inject constructor() : BaseMvpFragment(), PaymentDetailsContract.View,
  PaymentDetailsAdapter.IView {

  @Inject
  lateinit var mPresenter: PaymentDetailsPresenter

  private var mPage = 1
  private lateinit var mDetailsAdapter: PaymentDetailsAdapter

  override fun getLayoutId(): Int {
    return R.layout.frag_payment_details
  }

  override fun initialize() {
    super.initialize()

    val adapters = LinkedList<DelegateAdapter.Adapter<*>>()
    mDetailsAdapter = PaymentDetailsAdapter(mContext, this)

    adapters.add(mDetailsAdapter)

    recyclerView.setAdapters(mContext, adapters)

    mPresenter.getPaymentDetails(mPage)


    refreshLayout.setOnRefreshListener {
      mPage = 1
      mPresenter.getPaymentDetails(mPage)

    }

    refreshLayout.setOnLoadMoreListener {
      mPage++
      mPresenter.getPaymentDetails(mPage)
    }
  }

  override fun onClickDetail(detail: PaymentDetail) {
    val activity = activity
    if (activity != null && activity is PaymentDetailsActivity) {
      activity.addNextFragment(detail)
    }
  }


  override fun showPaymentDetails(success: Boolean, result: PaymentDetails?) {

    if (result != null) {
      refreshLayout.setEnableLoadMore(result.pageCount > mPage)
    }

    if (mPage == 1) {
      refreshLayout.finishRefresh(success)
    } else {
      refreshLayout.finishLoadMore(success)
    }

    if (mPage == 1) {
      mDetailsAdapter.setDatas(result?.list)
    } else {
      mDetailsAdapter.addDatas(result?.list)
    }
  }
}
