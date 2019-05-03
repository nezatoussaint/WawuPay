package com.hviewtech.wawupay.ui.activity.profile

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.DateUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import kotlinx.android.synthetic.main.act_transaction_details.*

class TransactionDetailsActivity : BaseActivity() {

  companion object {
    val DETAIL = "detail"
  }

  override fun getLayoutId(): Int {
    return R.layout.act_transaction_details
  }

  override fun initialize() {
    super.initialize()

    val intent = intent
    if (intent != null) {
      val detail = intent.getSerializableExtra(DETAIL) as? PaymentDetail
      if (detail == null) {
        finish()
        return
      }


      tv_amount.value = NumberUtils.toPlainString(detail.amount) + "Rwf"
      platformFee.value = (if (detail.type == 1) "+" else "-") + NumberUtils.toPlainString(detail.amount) + "Rwf"
      val categories = resources.getStringArray(R.array.transaction_category)
      if (categories.size >= detail.category) {
        tvCategory.value = categories[detail.category]
      } else {
        tvCategory.value = "Other"
      }
      val info = HawkExt.info
      payerId.value = if (detail.type == 1) detail.relativeNum else info?.num
      receiverId.value = if (detail.type == 2) detail.relativeNum else info?.num

      paymentTime.value = DateUtils.formatDataTime(detail.createTime)
      receiveTime.value = DateUtils.formatDataTime(detail.createTime)

      orderNo.value = detail.orderNo

    }
  }

}
