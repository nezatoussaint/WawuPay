package com.hviewtech.wawupay.ui.adapter.profile

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.logd
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.DateUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.ui.activity.profile.ReportActivity
import com.hviewtech.wawupay.ui.activity.profile.TransactionDetailsActivity
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter


/**
 * @author su
 * @date 2018/3/25
 * @description
 */

class TransactionLogsAdapter(context: Context) : BaseAdapter<PaymentDetail>(context) {

  private val categories: Array<String>


  init {
    categories = context.resources.getStringArray(R.array.transaction_category)
  }

  override fun getLayoutId(): Int {
    return R.layout.item_transaction_logs
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    return LinearLayoutHelper()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val createTime = holder.getView<TextView>(R.id.createTime)
    val amount = holder.getView<TextView>(R.id.amount)
    val category = holder.getView<TextView>(R.id.category)
    val orderNo = holder.getView<TextView>(R.id.orderNo)

    val toNickname = holder.getView<TextView>(R.id.toNickname)
    val fromNickname = holder.getView<TextView>(R.id.fromNickname)
    val report = holder.getView<Button>(R.id.report)

    val info = HawkExt.info

    val detail = getItem(position)

    createTime?.value = DateUtils.formatDataTime(detail.createTime)

    amount?.value = (if (detail.type == 1) "+" else "-") + NumberUtils.toPlainString(detail.amount) + "Rwf"

    category?.value = if (detail.category > 0) categories[detail.category] else ""

    toNickname?.value = if (detail.type == 2) detail.relativeName else info?.nickname
    fromNickname?.value = if (detail.type == 1) detail.relativeName else info?.nickname

    orderNo?.value = detail.orderNo

    report?.setOnClickListener {
      val intent = Intent(mContext, ReportActivity::class.java)
      intent.putExtra(ReportActivity.DETAIL, detail)
      mContext.goIntent(intent)
    }

    holder.itemView.setOnClickListener {
      val intent = Intent(mContext, TransactionDetailsActivity::class.java)
      intent.putExtra(TransactionDetailsActivity.DETAIL, detail)
      mContext.goIntent(intent)

    }

  }
}
