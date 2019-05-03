package com.hviewtech.wawupay.ui.adapter.utitlities

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.utility.TopupOrder
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.item_topup_orders.view.*
import net.cachapa.expandablelayout.ExpandableLayout


class TopupOrdersAdapter constructor(context: Context) : BaseAdapter<TopupOrder>(context) {


  override fun getLayoutId(): Int {
    return R.layout.item_topup_orders
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    helper.paddingLeft = DensityUtil.dp2px(16f)
    helper.paddingRight = DensityUtil.dp2px(16f)
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
            parent.smoothScrollToPosition(holder.adapterPosition);
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

    it.orderId.value = item.orderNo
    it.phone.value = item.phoneNumber
    it.amount.value = "${NumberUtils.toPlainString(item.amount)} Rwf"
    it.provider.value = item.companyName
    it.date.value = item.date


  }
}