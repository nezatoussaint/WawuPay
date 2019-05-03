package com.hviewtech.wawupay.ui.activity

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity

class TestActivity : BaseActivity() {
  override fun getLayoutId(): Int {
    return R.layout.act_test

  }

  override fun initialize() {
    super.initialize()


//    val list = ArrayList<String>()
//    for (i in 0..20) {
//      list.add("下拉列表项" + (i + 1))
//    }
//    listView.itemsData = (list)

  }
}
