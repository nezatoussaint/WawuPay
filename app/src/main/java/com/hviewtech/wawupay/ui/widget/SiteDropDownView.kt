package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.transport.Site

class SiteDropDownView : BaseDropDownView<Site> {

  override val textId: Int
    get() = R.id.text
  override val layoutId: Int
    get() = R.layout.view_dropdownlist_search


  override val itemRootId: Int
    get() = R.layout.view_dropdownlist_searchroot


  override val itemLayoutId: Int
    get() = R.layout.view_dropdownlist_searchitem


  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


  override fun initText(textView: TextView, item: Site?, init: Boolean) {

    textView.text = item?.siteName

  }

}