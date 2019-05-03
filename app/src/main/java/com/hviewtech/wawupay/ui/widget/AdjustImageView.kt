package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

class AdjustImageView : android.support.v7.widget.AppCompatImageView {
  constructor(context: Context) : super(context) {
    adjustViewBounds = true
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    adjustViewBounds = true
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    adjustViewBounds = true
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val d = drawable
    if (d != null) {
      val width = View.MeasureSpec.getSize(widthMeasureSpec)
      val height = Math.ceil((width.toFloat() * d.intrinsicHeight.toFloat() / d.intrinsicWidth.toFloat()).toDouble()).toInt()
      setMeasuredDimension(width, height)

    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }
  }
}