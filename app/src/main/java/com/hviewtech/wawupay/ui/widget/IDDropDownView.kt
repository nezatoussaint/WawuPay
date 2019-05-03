package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.decoration.DividerItemDecoration

class IDDropDownView : BaseDropDownView<String> {

  override val textId: Int
    get() = R.id.text
  override val layoutId: Int
    get() = R.layout.view_dropdownlist_idtype


  override val itemRootId: Int
    get() = R.layout.view_dropdownlist_idtyperoot


  override val itemLayoutId: Int
    get() = R.layout.view_dropdownlist_idtypeitem


  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

  override fun initDivider(decoration: DividerItemDecoration) {
    decoration.setColorResource(R.color.colorPrimary)
  }

  override fun initText(textView: TextView, item: String?, init: Boolean) {

    if (init) {
      return
    }
    textView.text = item

  }

}