/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.hviewtech.wawupay.common.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.util.DensityUtil

/**
 * DividerItemDecoration is a [RecyclerView.ItemDecoration] that can be used as a divider
 * between items of a [LinearLayoutManager]. It supports both [.HORIZONTAL] and
 * [.VERTICAL] orientations.
 *
 * <pre>
 * mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 * mLayoutManager.getOrientation());
 * recyclerView.addItemDecoration(mDividerItemDecoration);
</pre> *
 */
class DividerItemDecoration
/**
 * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
 * [LinearLayoutManager].
 *
 * @param context Current context, it will be used to access resources.
 * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
 */
  (val context: Context, orientation: Int) : RecyclerView.ItemDecoration() {

  constructor(context: Context) : this(context, VERTICAL)

  private var mPaint: Paint
  var dividerHeight: Int = DensityUtil.dp2px(0.5f)
    set(value) {
      field = value
    }
  var dividerWidth: Int = DensityUtil.dp2px(0.5f)
    set(value) {
      field = value
    }

  /**
   * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
   */
  private var mOrientation: Int = VERTICAL

  private val mBounds = Rect()

  init {
    val a = context.obtainStyledAttributes(ATTRS)
    mPaint = Paint()
    mPaint.color = 0xFFE5E5E5.toInt()
    a.recycle()
    setOrientation(orientation)
  }

  /**
   * Sets the orientation for this divider. This should be called if
   * [RecyclerView.LayoutManager] changes orientation.
   *
   * @param orientation [.HORIZONTAL] or [.VERTICAL]
   */
  fun setOrientation(orientation: Int) {
    if (orientation != HORIZONTAL && orientation != VERTICAL) {
      throw IllegalArgumentException(
        "Invalid orientation. It should be either HORIZONTAL or VERTICAL"
      )
    }
    mOrientation = orientation
  }

  /**
   * Sets the [Drawable] for this divider.
   *
   * @param drawable Drawable that should be used as a divider.
   */
  fun setColor(color: Long) {
    mPaint.color = color.toInt()
  }

  fun setColor(color: Int) {
    mPaint.color = color
  }

  fun setColorResource(@ColorRes color: Int) {
    mPaint.color = context.resources.getColor(color)
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
    if (parent.layoutManager == null) {
      return
    }
    if (mOrientation == VERTICAL) {
      drawVertical(c, parent)
    } else {
      drawHorizontal(c, parent)
    }
  }

  private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
    canvas.save()
    val left: Int
    val right: Int

    if (parent.clipToPadding) {
      left = parent.paddingLeft
      right = parent.width - parent.paddingRight
      canvas.clipRect(
        left, parent.paddingTop, right,
        parent.height - parent.paddingBottom
      )
    } else {
      left = 0
      right = parent.width
    }

    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      parent.getDecoratedBoundsWithMargins(child, mBounds)
      val bottom = mBounds.bottom + Math.round(child.translationY)
      val top = bottom - dividerHeight
      canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }
    canvas.restore()
  }

  private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
    canvas.save()
    val top: Int
    val bottom: Int

    if (parent.clipToPadding) {
      top = parent.paddingTop
      bottom = parent.height - parent.paddingBottom
      canvas.clipRect(
        parent.paddingLeft, top,
        parent.width - parent.paddingRight, bottom
      )
    } else {
      top = 0
      bottom = parent.height
    }

    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      parent.layoutManager.getDecoratedBoundsWithMargins(child, mBounds)
      val right = mBounds.right + Math.round(child.translationX)
      val left = right - dividerWidth
      canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }
    canvas.restore()
  }

  override fun getItemOffsets(
    outRect: Rect, view: View, parent: RecyclerView,
    state: RecyclerView.State?
  ) {
    if (mOrientation == VERTICAL) {
      outRect.set(0, 0, 0, dividerHeight)
    } else {
      outRect.set(0, 0, dividerWidth, 0)
    }
  }

  companion object {
    val HORIZONTAL = LinearLayout.HORIZONTAL
    val VERTICAL = LinearLayout.VERTICAL

    private val TAG = "DividerItem"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
  }
}
