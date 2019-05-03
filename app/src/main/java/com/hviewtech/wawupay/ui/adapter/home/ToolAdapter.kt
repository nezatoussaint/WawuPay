package com.hviewtech.wawupay.ui.adapter.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.local.App
import com.hviewtech.wawupay.bean.local.Tool
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.activity.electricity.ElectricityActivity
import com.hviewtech.wawupay.ui.activity.ticket.TicketSearchActivity
import com.hviewtech.wawupay.ui.activity.topup.TopupActivity
import com.hviewtech.wawupay.ui.activity.water.WaterActivity
import com.hviewtech.wawupay.ui.adapter.base.AppViewHolder
import com.hviewtech.wawupay.ui.adapter.base.BaseArrayAdapter
import com.hviewtech.wawupay.ui.adapter.base.BaseOriginAdapter

class ToolAdapter(context: Context) : BaseOriginAdapter<Tool>(context) {
  val VIEW_TYPE_ITEM = 1
  val VIEW_TYPE_FOOTER = 2

  override fun getLayoutId(): Int {
    return R.layout.item_home_tool
  }

  override fun getItemViewType(position: Int): Int {
    return if (position == itemCount - 1) {
      VIEW_TYPE_FOOTER
    } else {
      VIEW_TYPE_ITEM
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val holder = super.onCreateViewHolder(parent, viewType)

    if (viewType == VIEW_TYPE_FOOTER) {
      holder.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.height)
    }

    return holder
  }


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    val item = getItem(position)

    val apps = holder.getView<GridView>(R.id.apps)

    val category1 = holder.getView<TextView>(R.id.category1)
    val category2 = holder.getView<TextView>(R.id.category2)

    val left = holder.getView<View>(R.id.left)
    val right = holder.getView<View>(R.id.right)

    if (position % 2 == 0) {

      left?.visibility = View.VISIBLE
      right?.visibility = View.GONE

      category1?.value = item.category


    } else {

      left?.visibility = View.GONE
      right?.visibility = View.VISIBLE

      category2?.value = item.category
    }

    val appAdapter = AppAdapter()
    appAdapter.setDatas(item.apps)
    apps?.adapter = appAdapter

  }


  class AppAdapter : BaseArrayAdapter<App>() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
      var view = convertView;
      val holder: AppViewHolder
      if (view == null) {
        view = View.inflate(parent.context, R.layout.item_home_toolapp, null)
        holder = AppViewHolder(view)
        view.tag = holder
      } else {
        holder = view.getTag() as AppViewHolder
      }

      val item = getItem(position)

      holder.getView<ImageView>(R.id.icon)?.setImageResource(item.icon)
      holder.getView<TextView>(R.id.name)?.value = item.name


      holder.itemView.setOnClickListener {

        when (item.icon) {
          R.drawable.bus_ticket_icon ->
            it.goActivity(TicketSearchActivity::class)

          R.drawable.phone_topup_icon ->
            it.goActivity(TopupActivity::class)

          R.drawable.water_icon ->
            it.goActivity(WaterActivity::class)

          R.drawable.electricity_icon ->
            it.goActivity(ElectricityActivity::class)


        }


      }

      return view
    }

  }
}
