package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.Gravity
import com.hviewtech.wawupay.R

class StateButton : AppCompatButton {


  //text color
  private var mNormalTextColor = 0
  private var mPressedTextColor = 0
  private var mUnableTextColor = 0
  private var mTextColorStateList: ColorStateList

  //animation duration
  private var mDuration = 0

  //radius
  private var mRadius = 0f
  private var mRound: Boolean = false

  //stroke
  private var mStrokeDashWidth = 0f
  private var mStrokeDashGap = 0f
  private var mNormalStrokeWidth = 0
  private var mPressedStrokeWidth = 0
  private var mUnableStrokeWidth = 0
  private var mNormalStrokeColor = 0
  private var mPressedStrokeColor = 0
  private var mUnableStrokeColor = 0

  //background color
  private var mNormalBackgroundColor = 0
  private var mPressedBackgroundColor = 0
  private var mUnableBackgroundColor = 0

  private var mNormalBackground: GradientDrawable
  private var mPressedBackground: GradientDrawable
  private var mUnableBackground: GradientDrawable

  private val states: Array<IntArray>

  private var mStateBackground: StateListDrawable


  init {
    states = Array(4) { IntArray(2) }
    mTextColorStateList = textColors
    //    val drawable = background
    //        if(drawable != null && drawable instanceof StateListDrawable){
    //            mStateBackground = (StateListDrawable) drawable;
    //        }else{
    mStateBackground = StateListDrawable()
    //        }

    mNormalBackground = GradientDrawable()
    mPressedBackground = GradientDrawable()
    mUnableBackground = GradientDrawable()

  }

  constructor(context: Context?) : super(context) {

  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    setup(attrs)
  }

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    setup(attrs)
  }

  private fun setup(attrs: AttributeSet?) {

    gravity = Gravity.CENTER

    //pressed, focused, normal, unable
    states[0] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed)
    states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
    states[3] = intArrayOf(-android.R.attr.state_enabled)
    states[2] = intArrayOf(android.R.attr.state_enabled)

    val a = context.obtainStyledAttributes(attrs, R.styleable.StateButton)

    //get original text color as default
    //set text color

    val mDefaultNormalTextColor = mTextColorStateList.getColorForState(states[2], currentTextColor)
    var mDefaultPressedTextColor = mTextColorStateList.getColorForState(states[0], currentTextColor)
    var mDefaultUnableTextColor = mTextColorStateList.getColorForState(states[3], currentTextColor)
    mNormalTextColor = a.getColor(R.styleable.StateButton_normalTextColor, mDefaultNormalTextColor)
    // 未设置就是用normal的
    if (mDefaultPressedTextColor == 0) {
      mDefaultPressedTextColor = mNormalTextColor
    }
    mPressedTextColor = a.getColor(R.styleable.StateButton_pressedTextColor, mDefaultPressedTextColor)
    // 未设置就是用normal的
    if (mDefaultUnableTextColor == 0) {
      mDefaultUnableTextColor = mNormalTextColor
    }
    mUnableTextColor = a.getColor(R.styleable.StateButton_unableTextColor, mDefaultUnableTextColor)
    setTextColor()

    //set animation duration
    mDuration = a.getInteger(R.styleable.StateButton_animationDuration, mDuration)
    mStateBackground.setEnterFadeDuration(mDuration)
    mStateBackground.setExitFadeDuration(mDuration)

    //set background color
    mNormalBackgroundColor = a.getColor(R.styleable.StateButton_normalBackgroundColor, 0)
    // 未设置就是用normal的
    mPressedBackgroundColor = a.getColor(R.styleable.StateButton_pressedBackgroundColor, mNormalBackgroundColor)
    mUnableBackgroundColor = a.getColor(R.styleable.StateButton_unableBackgroundColor, mNormalBackgroundColor)
    mNormalBackground.setColor(mNormalBackgroundColor)
    mPressedBackground.setColor(mPressedBackgroundColor)
    mUnableBackground.setColor(mUnableBackgroundColor)

    //set radius
    mRadius = a.getDimensionPixelSize(R.styleable.StateButton_radius, 0).toFloat()
    mRound = a.getBoolean(R.styleable.StateButton_round, false)
    mNormalBackground.cornerRadius = mRadius
    mPressedBackground.cornerRadius = mRadius
    mUnableBackground.cornerRadius = mRadius

    //set stroke
    mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth, 0).toFloat()
    mStrokeDashGap = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth, 0).toFloat()
    mNormalStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_normalStrokeWidth, 0)
    // 未设置就是用normal的
    mPressedStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_pressedStrokeWidth, mNormalStrokeWidth)
    mUnableStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_unableStrokeWidth, mNormalStrokeWidth)
    mNormalStrokeColor = a.getColor(R.styleable.StateButton_normalStrokeColor, 0)
    // 未设置就是用normal的
    mPressedStrokeColor = a.getColor(R.styleable.StateButton_pressedStrokeColor, mNormalStrokeColor)
    mUnableStrokeColor = a.getColor(R.styleable.StateButton_unableStrokeColor, mNormalStrokeColor)
    setStroke()

    //set background
    mStateBackground.addState(states[0], mPressedBackground)
    mStateBackground.addState(states[1], mPressedBackground)
    mStateBackground.addState(states[3], mUnableBackground)
    mStateBackground.addState(states[2], mNormalBackground)
    setBackgroundDrawable(mStateBackground)
    a.recycle()
  }


  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    setRound(mRound)
  }

  /****************** stroke color  */

  fun setNormalStrokeColor(@ColorInt normalStrokeColor: Int) {
    this.mNormalStrokeColor = normalStrokeColor
    setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth)
  }

  fun setPressedStrokeColor(@ColorInt pressedStrokeColor: Int) {
    this.mPressedStrokeColor = pressedStrokeColor
    setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth)
  }

  fun setUnableStrokeColor(@ColorInt unableStrokeColor: Int) {
    this.mUnableStrokeColor = unableStrokeColor
    setStroke(mUnableBackground, mUnableStrokeColor, mUnableStrokeWidth)
  }

  fun setStateStrokeColor(@ColorInt normal: Int, @ColorInt pressed: Int, @ColorInt unable: Int) {
    mNormalStrokeColor = normal
    mPressedStrokeColor = pressed
    mUnableStrokeColor = unable
    setStroke()
  }

  /****************** stroke width  */

  fun setNormalStrokeWidth(normalStrokeWidth: Int) {
    this.mNormalStrokeWidth = normalStrokeWidth
    setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth)
  }

  fun setPressedStrokeWidth(pressedStrokeWidth: Int) {
    this.mPressedStrokeWidth = pressedStrokeWidth
    setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth)
  }

  fun setUnableStrokeWidth(unableStrokeWidth: Int) {
    this.mUnableStrokeWidth = unableStrokeWidth
    setStroke(mUnableBackground, mUnableStrokeColor, mUnableStrokeWidth)
  }

  fun setStateStrokeWidth(normal: Int, pressed: Int, unable: Int) {
    mNormalStrokeWidth = normal
    mPressedStrokeWidth = pressed
    mUnableStrokeWidth = unable
    setStroke()
  }

  fun setStrokeDash(strokeDashWidth: Float, strokeDashGap: Float) {
    this.mStrokeDashWidth = strokeDashWidth
    this.mStrokeDashGap = strokeDashWidth
    setStroke()
  }

  private fun setStroke() {
    setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth)
    setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth)
    setStroke(mUnableBackground, mUnableStrokeColor, mUnableStrokeWidth)
  }

  private fun setStroke(mBackground: GradientDrawable, mStrokeColor: Int, mStrokeWidth: Int) {
    mBackground.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashWidth, mStrokeDashGap)
  }

  /********************   radius   */

  fun setRadius(@FloatRange(from = 0.0) radius: Float) {
    this.mRadius = radius
    mNormalBackground.cornerRadius = mRadius
    mPressedBackground.cornerRadius = mRadius
    mUnableBackground.cornerRadius = mRadius
  }

  fun setRound(round: Boolean) {
    this.mRound = round
    val height = measuredHeight
    if (mRound) {
      setRadius(height / 2f)
    }
  }

  fun setRadius(radii: FloatArray) {
    mNormalBackground.cornerRadii = radii
    mPressedBackground.cornerRadii = radii
    mUnableBackground.cornerRadii = radii
  }

  /********************  background color   */

  fun setStateBackgroundColor(@ColorInt normal: Int, @ColorInt pressed: Int, @ColorInt unable: Int) {
    mNormalBackgroundColor = normal
    mPressedBackgroundColor = pressed
    mUnableBackgroundColor = unable
    mNormalBackground.setColor(mNormalBackgroundColor)
    mPressedBackground.setColor(mPressedBackgroundColor)
    mUnableBackground.setColor(mUnableBackgroundColor)
  }

  fun setNormalBackgroundColor(@ColorInt normalBackgroundColor: Int) {
    this.mNormalBackgroundColor = normalBackgroundColor
    mNormalBackground.setColor(mNormalBackgroundColor)
  }

  fun setPressedBackgroundColor(@ColorInt pressedBackgroundColor: Int) {
    this.mPressedBackgroundColor = pressedBackgroundColor
    mPressedBackground.setColor(mPressedBackgroundColor)
  }

  fun setUnableBackgroundColor(@ColorInt unableBackgroundColor: Int) {
    this.mUnableBackgroundColor = unableBackgroundColor
    mUnableBackground.setColor(mUnableBackgroundColor)
  }

  /*******************alpha animation duration */
  fun setAnimationDuration(@IntRange(from = 0) duration: Int) {
    this.mDuration = duration
    mStateBackground.setEnterFadeDuration(mDuration)
  }

  /***************  text color    */

  private fun setTextColor() {
    val colors = intArrayOf(mPressedTextColor, mPressedTextColor, mNormalTextColor, mUnableTextColor)
    mTextColorStateList = ColorStateList(states, colors)
    setTextColor(mTextColorStateList)
  }

  fun setStateTextColor(@ColorInt normal: Int, @ColorInt pressed: Int, @ColorInt unable: Int) {
    this.mNormalTextColor = normal
    this.mPressedTextColor = pressed
    this.mUnableTextColor = unable
    setTextColor()
  }

  fun setNormalTextColor(@ColorInt normalTextColor: Int) {
    this.mNormalTextColor = normalTextColor
    setTextColor()

  }

  fun setPressedTextColor(@ColorInt pressedTextColor: Int) {
    this.mPressedTextColor = pressedTextColor
    setTextColor()
  }

  fun setUnableTextColor(@ColorInt unableTextColor: Int) {
    this.mUnableTextColor = unableTextColor
    setTextColor()
  }

}
