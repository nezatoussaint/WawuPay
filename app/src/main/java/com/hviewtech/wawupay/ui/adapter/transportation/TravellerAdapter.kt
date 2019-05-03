package com.hviewtech.wawupay.ui.adapter.transportation

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.local.Traveller
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil

class TravellerAdapter constructor(context: Context) : BaseAdapter<Traveller>(context) {


  override fun getLayoutId(): Int {
    return R.layout.item_traveller
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    helper.setDividerHeight(DensityUtil.dp2px(10f))
    helper.marginBottom = DensityUtil.dp2px(20f)
    return helper
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val ivPosition = holder.getView<ImageView>(R.id.ivPosition)
    val tvPosition = holder.getView<TextView>(R.id.tvPosition)
    val name = holder.getView<TextView>(R.id.name)
    val phoneNumber = holder.getView<TextView>(R.id.phoneNumber)

    val item = getItem(position)

    if (position % 2 == 0) {
      ivPosition?.setImageResource(R.drawable.elpse_number)
    } else {
      ivPosition?.setImageResource(R.drawable.elpse_blue)
    }
    tvPosition?.value = "${position + 1}"

    name?.value = "${item.lastName} ${item.firstName}"
    phoneNumber?.value = item.phoneNumber

  }

}