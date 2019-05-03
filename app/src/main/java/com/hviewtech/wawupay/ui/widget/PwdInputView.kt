package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Created by lumingmin on 16/6/16.
 */

class PwdInputView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  android.support.v7.widget.AppCompatEditText(context, attrs, defStyleAttr) {
  private var mPaint: Paint? = null
  private var mPaintContent: Paint? = null
  private lateinit var mPaintArc: Paint
  private val mPadding = 1
  private var radiusBg: Int = 0
  private var radiusArc: Float = 0.toFloat()
  private val radiusArc_last: Float = 0.toFloat()
  private val PaintLastArcAnimDuration = 100
  private var mTextSize: Int = 0
  private var mPaintLastArcAnim: PaintLastArcAnim? = null
  private var textLength: Int = 0
  private var maxLineSize: Int = 0
  private var addText = true
  private var interpolatedTime: Float = 0.toFloat()
  private var isShadowPasswords = false

  private var defaultStr: String = ""
  private var defalutPicId = -1

  private var mViewType = ViewType.DEFAULT

  private var bgColor = Color.WHITE
  private var underLineColor = Color.GRAY
  private var paintColor = Color.argb(155, 0, 0, 0)

  val maxLength: Int
    get() {
      var length = 10
      try {
        val inputFilters = filters
        for (filter in inputFilters) {
          val c = filter.javaClass
          if (c.name == "android.text.InputFilter\$LengthFilter") {
            val f = c.declaredFields
            for (field in f) {
              if (field.name == "mMax") {
                field.isAccessible = true
                length = field.get(filter) as Int
              }
            }
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
      return length
    }

  init {
    initPaint()
    setOnLongClickListener { true }

  }

  enum class ViewType {
    DEFAULT, UNDERLINE, BIASLINE
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    //        setLayerType(LAYER_TYPE_SOFTWARE, null);
    // 背景
    val rectIn = RectF(
      mPadding.toFloat(),
      mPadding.toFloat(),
      (measuredWidth - mPadding).toFloat(),
      (measuredHeight - mPadding).toFloat()
    )
    canvas.drawRoundRect(rectIn, radiusBg.toFloat(), radiusBg.toFloat(), mPaintContent!!)
    // 边框
    val rect = RectF(
      mPadding.toFloat(),
      mPadding.toFloat(),
      (measuredWidth - mPadding).toFloat(),
      (measuredHeight - mPadding).toFloat()
    )

    if (radiusBg != -1) {

      mPaint!!.strokeWidth = 0.8f
      canvas.drawRoundRect(rect, radiusBg.toFloat(), radiusBg.toFloat(), mPaint!!)
    } else {
      mPaint!!.style = Paint.Style.FILL
      mPaint!!.color = bgColor
      canvas.drawRoundRect(rect, 0f, 0f, mPaint!!)

    }
    mPaint!!.style = Paint.Style.STROKE
    mPaint!!.color = Color.GRAY

    var cx: Float
    val cy = (measuredHeight / 2).toFloat()
    val half = (measuredWidth / maxLineSize / 2).toFloat()

    when (mViewType) {
      PwdInputView.ViewType.DEFAULT -> {
        mPaint!!.strokeWidth = 0.5f
        for (i in 1 until maxLineSize) {
          val x = (measuredWidth * i / maxLineSize).toFloat()
          canvas.drawLine(x, 0f, x, measuredHeight.toFloat(), mPaint!!)
        }
      }

      PwdInputView.ViewType.UNDERLINE -> {
        mPaint!!.strokeWidth = 4.0f
        mPaint!!.color = underLineColor
        for (i in text.toString().length until maxLineSize) {
          val x = measuredWidth * i / maxLineSize + half
          canvas.drawLine(
            x - half / 3,
            (measuredHeight - measuredHeight / 4).toFloat(),
            x + half / 3,
            (measuredHeight - measuredHeight / 4).toFloat(),
            mPaint!!
          )
        }
      }

      PwdInputView.ViewType.BIASLINE -> {
        mPaint!!.strokeWidth = 3.0f
        for (i in text.toString().length until maxLineSize) {
          val x = measuredWidth * i / maxLineSize + half
          canvas.drawLine(
            x + half / 8,
            measuredHeight / 2 - half / 4,
            x - half / 8,
            measuredHeight / 2 + half / 4,
            mPaint!!
          )
        }
      }
      else -> {
      }
    }

    mPaintArc.color = paintColor

    if (isShadowPasswords) {

      mPaintArc.textSize = mTextSize.toFloat()

      var bitmap: Bitmap? = null
      for (i in 0 until textLength) {
        cx = measuredWidth * i / maxLineSize + half

        if (defalutPicId != -1) {
          var scale = 1f
          if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.resources, defalutPicId)
            scale = (bitmap!!.height / bitmap.width).toFloat()
            bitmap = setBitmapSize(bitmap, half.toInt(), scale)
          }
          canvas.drawBitmap(bitmap, cx - half / 2, cy - half * scale / 2, mPaintArc)
        } else {
          val text: String
          if (defaultStr.isBlank()) {
            text = getText().toString()[i].toString()
          } else {
            text = defaultStr
          }
          canvas.drawText(
            text,
            cx - getFontlength(mPaintArc, text) / 2.0f,
            cy + getFontHeight(mPaintArc, text) / 2.0f,
            mPaintArc
          )

        }


      }

    } else {

      for (i in 0 until maxLineSize) {
        cx = measuredWidth * i / maxLineSize + half


        if (addText) {
          if (i < textLength - 1) {
            canvas.drawCircle(cx, cy, radiusArc, mPaintArc!!)
          } else if (i == textLength - 1) {

            canvas.drawCircle(cx, cy, radiusArc * interpolatedTime, mPaintArc!!)
          }
        } else {
          if (i < textLength) {
            canvas.drawCircle(cx, cy, radiusArc, mPaintArc!!)
          } else if (i == textLength) {
            canvas.drawCircle(cx, cy, radiusArc - radiusArc * interpolatedTime, mPaintArc!!)
          }
        }
      }
    }

  }

  override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter)
    if (text.toString().length - this.textLength >= 0) {
      addText = true
    } else {
      addText = false
    }

    this.textLength = text.toString().length
    if (textLength <= maxLength) {


      if (mPaintLastArcAnim != null) {
        clearAnimation()
        startAnimation(mPaintLastArcAnim)
      } else {
        invalidate()
      }
    }
  }

  protected fun initPaint() {
    maxLineSize = maxLength
    mPaintLastArcAnim = PaintLastArcAnim()
    mPaintLastArcAnim!!.duration = PaintLastArcAnimDuration.toLong()
    radiusBg = dip2px(4f)
    radiusArc = dip2px(4f).toFloat()
    mTextSize = dip2px(15f)
    textLength = 0
    mPaint = Paint()
    mPaint!!.isAntiAlias = true
    mPaint!!.style = Paint.Style.STROKE
    mPaint!!.color = Color.GRAY

    mPaintContent = Paint()
    mPaintContent!!.isAntiAlias = true
    mPaintContent!!.style = Paint.Style.FILL
    mPaintContent!!.color = Color.WHITE

    mPaintArc = Paint()
    mPaintArc.isAntiAlias = true
    mPaintArc.style = Paint.Style.FILL

  }

  private inner class PaintLastArcAnim : Animation() {
    override fun applyTransformation(time: Float, t: Transformation) {
      super.applyTransformation(time, t)
      //            radiusArc_last = radiusArc * interpolatedTime;
      interpolatedTime = time
      postInvalidate()


    }
  }

  fun dip2px(dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
  }

  fun getFontlength(paint: Paint, str: String): Float {
    val rect = Rect()
    paint.getTextBounds(str, 0, str.length, rect)
    return rect.width().toFloat()
  }

  fun getFontHeight(paint: Paint, str: String): Float {
    val rect = Rect()
    paint.getTextBounds(str, 0, str.length, rect)
    return rect.height().toFloat()

  }


  fun setShadowPasswords(show: Boolean) {
    this.isShadowPasswords = show
    this.defalutPicId = -1
    this.defaultStr = ""
    postInvalidate()

  }


  fun setShadowPasswords(show: Boolean, defaultStr: String) {
    this.isShadowPasswords = show
    this.defalutPicId = -1
    this.defaultStr = defaultStr
    postInvalidate()

  }

  fun setShadowPasswords(show: Boolean, picId: Int) {
    this.isShadowPasswords = show
    this.defalutPicId = picId
    this.defaultStr = ""
    postInvalidate()

  }

  private fun setBitmapSize(b: Bitmap, w: Int, s: Float): Bitmap {
    var b = b
    b = Bitmap.createScaledBitmap(b, w, (w * s).toInt(), true)
    return b

  }


  fun setPwdInputViewType(type: ViewType) {
    this.mViewType = type

  }

  fun setRadiusBg(radiusBg: Int) {
    this.radiusBg = radiusBg
  }

  fun setBgColor(bgColor: Int) {
    this.bgColor = bgColor
  }

  fun setNumTextSize(textsize: Int) {
    this.mTextSize = textsize
  }

  fun setUnderLineColor(underLineColor: Int) {
    this.underLineColor = underLineColor
  }

  fun setNumTextColor(textColor: Int) {
    this.paintColor = textColor
  }


}
