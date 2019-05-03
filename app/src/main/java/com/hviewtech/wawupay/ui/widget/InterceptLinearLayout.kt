package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class InterceptLinearLayout : LinearLayout {

  constructor(context: Context?) : super(context) {
    isFocusable = true
    isFocusableInTouchMode = true
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    isFocusable = true
    isFocusableInTouchMode = true
  }

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    isFocusable = true
    isFocusableInTouchMode = true
  }

  override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    return true
  }

}