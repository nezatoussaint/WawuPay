package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.utils.StatusBarUtil
import com.hviewtech.wawupay.ui.widget.listener.DeclaredOnClickListener
import kotlinx.android.synthetic.main.layout_title.view.*

/**
 * Only for activity
 */
class TitleView : LinearLayout {

  private val mContext: Context
  private var moreType: Int = 0

  constructor(context: Context) : super(context) {
    mContext = context
    orientation = VERTICAL

  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    mContext = context
    orientation = VERTICAL
    initView();
    initAttrs(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    mContext = context
    orientation = VERTICAL
    initAttrs(attrs)
  }

  private fun initView() {
    View.inflate(mContext, R.layout.layout_title, this)
  }

  private fun initAttrs(attrs: AttributeSet?) {
    attrs ?: return

    val a = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView)
    // 标题
    val title = a.getString(R.styleable.TitleView_app_title)
    app_title.text = title
    // 状态栏
    val fakeStatusBar = a.getBoolean(R.styleable.TitleView_app_fakeStatusBar, true)
    setFakeStatusBar(fakeStatusBar)

    // 后退键
    val showBack = a.getBoolean(R.styleable.TitleView_app_showBack, true)
    val useDefaultBack = a.getBoolean(R.styleable.TitleView_app_useDefaultBack, true)
    var backMethodName = a.getString(R.styleable.TitleView_app_backClick)

    if (showBack) {
      app_return.visibility = View.VISIBLE
    } else {
      app_return.visibility = View.INVISIBLE
    }
    if (backMethodName.isNullOrBlank() && useDefaultBack) {
      backMethodName = "onBackPressed"
    }
    if (!backMethodName.isNullOrBlank()) {
      app_return.setOnClickListener(DeclaredOnClickListener(this, backMethodName))
    }

    // 更多键
    val moreMethodName = a.getString(R.styleable.TitleView_app_moreClick)

    moreType = a.getInt(R.styleable.TitleView_app_moreType, 0)
    if (moreType == 0) {
      val text = a.getString(R.styleable.TitleView_app_moreContent)
      app_tvmore.text = text
      app_tvmore.visibility = View.VISIBLE
      app_ivmore.visibility = View.INVISIBLE

      app_ivmore.setOnClickListener(null)
      if (!TextUtils.isEmpty(moreMethodName)) {
        app_tvmore.setOnClickListener(DeclaredOnClickListener(this, moreMethodName))
      }

    } else {
      val drawable = a.getResourceId(R.styleable.TitleView_app_moreContent, android.R.drawable.ic_input_add)
      app_tvmore.visibility = View.INVISIBLE
      app_ivmore.visibility = View.VISIBLE
      app_ivmore.setImageResource(drawable)

      app_tvmore.setOnClickListener(null)
      if (!TextUtils.isEmpty(moreMethodName)) {
        app_ivmore.setOnClickListener(DeclaredOnClickListener(this, moreMethodName))
      }
    }
    a.recycle()
  }


  private fun setFakeStatusBar(show: Boolean) {
    // 如果包含状态栏占位View就设置status_bar_height
    val params = app_statusbar.layoutParams
    if (show) {
      val height = StatusBarUtil.getStatusBarHeight(mContext)
      params.height = height
      root_content.layoutParams.height = real_content.layoutParams.height + height
    } else {
      params.height = 0
      root_content.layoutParams.height = real_content.layoutParams.height
    }
  }


  var title: String? = null
    set(value) {
      app_title?.text = value
    }

  fun setTitle(title: CharSequence?) {
    app_title?.text = title
  }


  fun setNavigation(isShow: Boolean) {
    app_return?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
  }

  fun setOnMoreClickListener(function: () -> Unit) {
    if (moreType == 0) {
      app_tvmore.setOnClickListener { function.invoke() }
    } else {
      app_ivmore.setOnClickListener { function.invoke() }
    }
  }

  fun setOnReturnClickListener(function: () -> Unit) {
    app_return.setOnClickListener { function.invoke() }
  }

}