package com.hviewtech.wawupay.common.ext

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.TextView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager


var EditText.value: String?
  get() = this.text?.toString()
  set(value) {
    this.setText(value)
  }

var EditText.valueKeepState: String?
  get() = this.text.toString()
  set(value) {
    this.setTextKeepState(value)
    if (!value.isNullOrBlank()) {
      this.setSelection(value.length)
    }
  }

var TextView.value: String?
  get() = this.text.toString()
  set(value) {

    this.setText(value)
  }


fun RecyclerView.setAdapters(
  context: Context, adapters: List<DelegateAdapter.Adapter<*>>
): DelegateAdapter {
  val layoutManager = VirtualLayoutManager(context)
  // 注意：当hasConsistItemType=true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。表示它们共享一个类型
  // 当hasConsistItemType=false的时候，不同子adapter之间的类型不共享
  val adapter = DelegateAdapter(layoutManager)
  adapter.setAdapters(adapters)

  this.layoutManager = layoutManager
  this.recycledViewPool = RecyclerView.RecycledViewPool()
  this.adapter = adapter
  return adapter
}
