package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.hviewtech.wawupay.R

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class ZXingCodeView : ZXingView {

  private var isFlashOpened = false
  private var flashLightView: View? = null

  private val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"

  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    initView(context)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    initView(context)

  }


  private fun initView(context: Context) {
    val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    flashLightView = View.inflate(context, R.layout.item_flashlight, null)
    val width = View.MeasureSpec.makeMeasureSpec(
      0,
      View.MeasureSpec.UNSPECIFIED
    )
    val height = View.MeasureSpec.makeMeasureSpec(
      0,
      View.MeasureSpec.UNSPECIFIED
    )
    flashLightView!!.measure(width, height)
    val measuredHeight = flashLightView!!.measuredHeight
    val topOffset = mScanBoxView.topOffset
    val rectHeight = mScanBoxView.rectHeight

    layoutParams.topMargin = topOffset + rectHeight - measuredHeight
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
    addView(flashLightView, layoutParams)

    flashLightView!!.setOnClickListener { _ ->
      if (isFlashOpened) {
        closeFlashlight()
      } else {
        openFlashlight()
      }
      isFlashOpened = !isFlashOpened
    }


  }


  fun enableFlash() {
    // 闪光灯未开  且光线暗   显示提示
    var tipText = getScanBoxView().getTipText() ?: ""
    if (!tipText.contains(ambientBrightnessTip) && !isFlashOpened) {
      getScanBoxView().setTipText(tipText + ambientBrightnessTip)
    }
    if (flashLightView != null) {
      flashLightView!!.isEnabled = true
      flashLightView!!.visibility = View.VISIBLE
    }
  }

  fun disableFlash() {
    // 光线亮   隐藏提示
    var tipText = getScanBoxView().getTipText() ?: ""
    if (tipText.contains(ambientBrightnessTip)) {
      tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
      getScanBoxView().setTipText(tipText)
    }
    if (isFlashOpened) {
      return
    }
    // 闪光灯未开  并且光线亮
    if (flashLightView != null && !isFlashOpened) {
      flashLightView!!.visibility = View.INVISIBLE
      flashLightView!!.isEnabled = false
    }
  }


  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
  }
}