package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.ext.value

class TopupDropDownView : BaseDropDownView<String> {

  override val textId: Int
    get() = R.id.text
  override val layoutId: Int
    get() = R.layout.view_dropdownlist_topup


  override val itemRootId: Int
    get() = R.layout.view_dropdownlist_topuproot


  override val itemLayoutId: Int
    get() = R.layout.view_dropdownlist_topupitem


  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


  override fun initText(textView: TextView, item: String?, init: Boolean) {

    if (init) {
      return
    }
    textView.value = item

  }

}