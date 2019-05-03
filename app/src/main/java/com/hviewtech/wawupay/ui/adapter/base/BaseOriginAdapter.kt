package com.hviewtech.wawupay.ui.adapter.base

import android.content.Context
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper

abstract class BaseOriginAdapter<B>(context: Context) : BaseAdapter<B>(context) {
  override fun onCreateLayoutHelper(): LayoutHelper {
    return LinearLayoutHelper()
  }
}