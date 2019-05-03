package com.hviewtech.wawupay.ui.fragment.wallet

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpFragment
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.DateUtils
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.di.scope.ActivityScope
import kotlinx.android.synthetic.main.frag_payment_detail.*
import javax.inject.Inject

@ActivityScope
class PaymentDetailFragment @Inject constructor() : BaseMvpFragment() {

  companion object {
    const val DETAIL = "detail"
  }

  override fun getLayoutId(): Int {
    return R.layout.frag_payment_detail
  }

  public override fun initialize() {

    initData()
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    if (!hidden) {
      initData()
    }
  }

  private fun initData() {
    val arguments = arguments
    if (arguments != null) {
      val detail = arguments.getSerializable(DETAIL) as? PaymentDetail?
      if (detail != null) {

        amount.value = (if (detail.type == 1) "+" else "-") + NumberUtils.toPlainString(detail.amount) + "Rwf"

        createTime.value = DateUtils.formatDataTime(detail.createTime)


        orderNo.value = detail.orderNo


        // 账单分类(1-转账 2-店铺付款 3-在线支付 4-AA账单 5-红包 6-提现 7-退款 8-充值)
        val categories = resources.getStringArray(R.array.transaction_category)
        if (categories.size >= detail.category) {
          type.value = categories[detail.category]
        }
      }
    }
  }

}
