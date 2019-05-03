package com.hviewtech.wawupay.ui.adapter.base

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

abstract class BaseArrayAdapter<T> : BaseAdapter() {

  protected val mDatas: MutableList<T> = ArrayList()

  override fun getCount(): Int {
    return mDatas.size
  }

  override fun getItem(position: Int): T {
    return mDatas[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  abstract override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?

  fun setDatas(datas: List<T>?) {
    this.mDatas.clear()
    if (datas != null) {
      this.mDatas.addAll(datas)
    }
    notifyDataSetChanged()
  }
}
