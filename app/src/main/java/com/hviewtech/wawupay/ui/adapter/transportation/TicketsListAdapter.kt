package com.hviewtech.wawupay.ui.adapter.transportation

import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.transport.SiteInfo
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.activity.ticket.TicketBookingActivity
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil


class TicketsListAdapter constructor(context: Context) : BaseAdapter<SiteInfo>(context) {

  override fun getLayoutId(): Int {
    return R.layout.item_tickets_list
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    helper.setDividerHeight(DensityUtil.dp2px(10f))
    helper.marginLeft = DensityUtil.dp2px(16f)
    helper.marginRight = DensityUtil.dp2px(16f)
    helper.marginTop = DensityUtil.dp2px(16f)
    helper.marginBottom = DensityUtil.dp2px(16f)
    return helper
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val companyName = holder.getView<TextView>(R.id.companyName)
    val destination = holder.getView<TextView>(R.id.destination)
    val time = holder.getView<TextView>(R.id.time)
    val amount = holder.getView<TextView>(R.id.amount)
    val tripTime = holder.getView<TextView>(R.id.tripTime)

    val item = getItem(position)

    companyName?.value = item.company_name
    destination?.value = item.destination_name
    time?.value = "${item.schedule_time}\n${item.start}-${item.end}"
    amount?.value = "${item.price} Rwf"
    tripTime?.value = item.trip_time


    holder.itemView.setOnClickListener {

      val intent = Intent(mContext, TicketBookingActivity::class.java)
      intent.putExtra(TicketBookingActivity.SITE_INFO, item)
      mContext.goIntent(intent)
    }
  }
}