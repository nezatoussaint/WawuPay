package com.hviewtech.wawupay.ui.activity.common

import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.common.ext.value
import kotlinx.android.synthetic.main.act_txt.*

class TxtActivity : BaseActivity() {
  companion object {
    val CONTENT = "content"
  }

  var content: String = ""
    get() = intent.getStringExtra(CONTENT)

  override fun getLayoutId(): Int {
    return R.layout.act_txt
  }


  override fun initialize() {
    super.initialize()

    tvContent.value = content

  }


}