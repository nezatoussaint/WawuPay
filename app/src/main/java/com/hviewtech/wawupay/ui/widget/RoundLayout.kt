package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hviewtech.wawupay.R

class RoundLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
  FrameLayout(context, attrs, defStyle) {

  private var topLeftRadius: Float = 0.toFloat()
  private var topRightRadius: Float = 0.toFloat()
  private var bottomLeftRadius: Float = 0.toFloat()
  private var bottomRightRadius: Float = 0.toFloat()

  private val roundPaint: Paint
  private val imagePaint: Paint

  init {
    if (attrs != null) {
      val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout)
      val roundradius = ta.getDimension(R.styleable.RoundLayout_roundradius, 0f)
      topLeftRadius = ta.getDimension(R.styleable.RoundLayout_topLeftRadius, roundradius)
      topRightRadius = ta.getDimension(R.styleable.RoundLayout_topRightRadius, roundradius)
      bottomLeftRadius = ta.getDimension(R.styleable.RoundLayout_bottomLeftRadius, roundradius)
      bottomRightRadius = ta.getDimension(R.styleable.RoundLayout_bottomRightRadius, roundradius)
      ta.recycle()
    }
    roundPaint = Paint()
    roundPaint.color = Color.WHITE
    roundPaint.isAntiAlias = true
    roundPaint.style = Paint.Style.FILL
    roundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

    imagePaint = Paint()
    imagePaint.xfermode = null
  }

  override fun dispatchDraw(canvas: Canvas) {
    canvas.saveLayer(RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat()), imagePaint, Canvas.ALL_SAVE_FLAG)
    super.dispatchDraw(canvas)
    drawTopLeft(canvas)
    drawTopRight(canvas)
    drawBottomLeft(canvas)
    drawBottomRight(canvas)
    canvas.restore()
  }

  private fun drawTopLeft(canvas: Canvas) {
    if (topLeftRadius > 0) {
      val path = Path()
      path.moveTo(0f, topLeftRadius)
      path.lineTo(0f, 0f)
      path.lineTo(topLeftRadius, 0f)
      path.arcTo(RectF(0f, 0f, topLeftRadius * 2, topLeftRadius * 2), -90f, -90f)
      path.close()
      canvas.drawPath(path, roundPaint)
    }
  }

  private fun drawTopRight(canvas: Canvas) {
    if (topRightRadius > 0) {
      val width = width
      val path = Path()
      path.moveTo(width - topRightRadius, 0f)
      path.lineTo(width.toFloat(), 0f)
      path.lineTo(width.toFloat(), topRightRadius)
      path.arcTo(RectF(width - 2 * topRightRadius, 0f, width.toFloat(), topRightRadius * 2), 0f, -90f)
      path.close()
      canvas.drawPath(path, roundPaint)
    }
  }

  private fun drawBottomLeft(canvas: Canvas) {
    if (bottomLeftRadius > 0) {
      val height = height
      val path = Path()
      path.moveTo(0f, height - bottomLeftRadius)
      path.lineTo(0f, height.toFloat())
      path.lineTo(bottomLeftRadius, height.toFloat())
      path.arcTo(RectF(0f, height - 2 * bottomLeftRadius, bottomLeftRadius * 2, height.toFloat()), 90f, 90f)
      path.close()
      canvas.drawPath(path, roundPaint)
    }
  }

  private fun drawBottomRight(canvas: Canvas) {
    if (bottomRightRadius > 0) {
      val height = height
      val width = width
      val path = Path()
      path.moveTo(width - bottomRightRadius, height.toFloat())
      path.lineTo(width.toFloat(), height.toFloat())
      path.lineTo(width.toFloat(), height - bottomRightRadius)
      path.arcTo(
        RectF(
          width - 2 * bottomRightRadius,
          height - 2 * bottomRightRadius,
          width.toFloat(),
          height.toFloat()
        ), 0f, 90f
      )
      path.close()
      canvas.drawPath(path, roundPaint)
    }
  }

  /**
   * 设置左上角圆角弧度
   *
   * @param topLeftRadius
   */
  fun setDrawTopLeft(topLeftRadius: Float) {
    this.topLeftRadius = topLeftRadius
    invalidate()
  }

  /**
   * 设置右上角圆角弧度
   *
   * @param topRightRadius
   */
  fun setDrawTopRight(topRightRadius: Float) {
    this.topRightRadius = topRightRadius
    invalidate()
  }

  /**
   * 设置左下角圆角弧度
   *
   * @param bottomLeftRadius
   */
  fun setDrawBottomLeft(bottomLeftRadius: Float) {
    this.bottomLeftRadius = bottomLeftRadius
    invalidate()
  }

  /**
   * 设置右下角圆角弧度
   *
   * @param bottomRightRadius
   */
  fun setDrawBottomRight(bottomRightRadius: Float) {
    this.bottomRightRadius = bottomRightRadius
    invalidate()
  }

  /**
   * 设置左上角与右下角圆角弧度，简称左对角线弧度
   *
   * @param radius
   */
  fun setLeftDiagonal(radius: Float) {
    this.topLeftRadius = radius
    this.bottomRightRadius = radius
    this.topRightRadius = 0.0f
    this.bottomLeftRadius = 0.0f
    invalidate()
  }

  /**
   * 设置右上角与左下角圆角弧度，简称右对角线弧度
   *
   * @param radius
   */
  fun setRightDiagonal(radius: Float) {
    this.topLeftRadius = 0.0f
    this.bottomRightRadius = 0.0f
    this.topRightRadius = radius
    this.bottomLeftRadius = radius
    invalidate()
  }

  /**
   * 设置左下角与右下角圆角弧度，简称下弧度
   *
   * @param radius
   */
  fun setBottomDiagonal(radius: Float) {
    this.topLeftRadius = 0.0f
    this.topRightRadius = 0.0f
    this.bottomRightRadius = radius
    this.bottomLeftRadius = radius
    invalidate()
  }

  /**
   * 设置右上角与左上角圆角弧度，简称上弧度
   *
   * @param radius
   */
  fun setTopDiagonal(radius: Float) {
    this.topLeftRadius = radius
    this.topRightRadius = radius
    this.bottomRightRadius = 0.0f
    this.bottomLeftRadius = 0.0f
    invalidate()
  }

  /**
   * 设置所有角度统一圆角弧度
   *
   * @param radius
   */
  fun setAllDiagonal(radius: Float) {
    this.topLeftRadius = radius
    this.bottomRightRadius = radius
    this.topRightRadius = radius
    this.bottomLeftRadius = radius
    invalidate()
  }
}