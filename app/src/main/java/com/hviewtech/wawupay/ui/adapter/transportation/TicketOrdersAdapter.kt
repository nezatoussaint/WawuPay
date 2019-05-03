package com.hviewtech.wawupay.ui.adapter.transportation

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.transport.Order
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.item_ticket_orders.view.*
import net.cachapa.expandablelayout.ExpandableLayout


class TicketOrdersAdapter constructor(context: Context) : BaseAdapter<Order>(context) {
  override fun getLayoutId(): Int {
    return R.layout.item_ticket_orders
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    helper.paddingLeft = DensityUtil.dp2px(16f)
    helper.paddingRight = DensityUtil.dp2px(16f)
    helper.paddingBottom = DensityUtil.dp2px(16f)
    helper.paddingTop = DensityUtil.dp2px(16f)
    return helper
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val it = holder.itemView

    it.btnExpand.setOnCheckedChangeListener(null)
    it.expandLayout.setExpanded(false, false)
    it.btnExpand.isChecked = false
    it.btnExpand.setOnCheckedChangeListener { buttonView, isChecked ->
      it.expandLayout.setExpanded(isChecked)
    }

    it.expandLayout.setOnExpansionUpdateListener { expansionFraction, state ->

      when (state) {
        ExpandableLayout.State.EXPANDING, ExpandableLayout.State.COLLAPSING -> {
          val parent = it.parent
          if (parent is RecyclerView) {
            parent.scrollToPosition(holder.adapterPosition);
          }
        }
      }

    }

    if (position % 2 == 0) {
      it.realLayout.setBackgroundResource(R.drawable.bg_refunded_orders)
      it.btnExpand.setButtonDrawable(R.drawable.icon_refunded_selector)
    } else {
      it.realLayout.setBackgroundResource(R.drawable.bg_departure_orders)
      it.btnExpand.setButtonDrawable(R.drawable.icon_departure_selector)
    }


    val item = getItem(position)
    it.ticketId.value = item.ticketId
    it.customer.value = item.customerName
    it.departure.value = item.destination

    it.agent.value = item.agent
    it.time.value = item.time
    it.price.value = "${item.totalAmount} Rwf"
    it.price2.value = "${item.totalAmount} Rwf"

    it.date.value = item.date
    it.ticketStatus.value = "Processing"
    it.printStatus.value = "Waiting"

    it.paymentTime.value = item.paymentTime
    it.paymentStatus.value = when (item.paymentStatus) {
      1 -> "Paied"
      2 -> "Refunded"
      else -> "For Departure"
    }

    when (item.status) {
      1 -> it.status.setBackgroundResource(R.drawable.completed_back)
      2 -> it.status.setBackgroundResource(R.drawable.refunded_back)
      else -> it.status.setBackgroundResource(R.drawable.departure_back)
    }

  }
}