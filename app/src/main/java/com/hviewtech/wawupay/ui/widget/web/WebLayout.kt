package com.hviewtech.wawupay.ui.widget.web

import android.app.Activity
import android.view.LayoutInflater
import android.webkit.WebView
import com.hviewtech.wawupay.R
import com.just.agentweb.IWebLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by cenxiaozhong on 2017/7/1.
 * source code  https://github.com/Justson/AgentWeb
 */

class WebLayout(private val mActivity: Activity) : IWebLayout<WebView, SmartRefreshLayout> {
  private val mSmartRefreshLayout: SmartRefreshLayout
  private var mWebView: WebView

  init {
    mSmartRefreshLayout = LayoutInflater.from(mActivity).inflate(R.layout.layout_web, null) as SmartRefreshLayout
    mWebView = mSmartRefreshLayout.findViewById(R.id.webView)


    mSmartRefreshLayout.setOnRefreshListener {
      it.finishRefresh(1000)
      mWebView.reload()
    }
  }

  override fun getLayout(): SmartRefreshLayout {
    return mSmartRefreshLayout
  }

  override fun getWebView(): WebView {
    return mWebView
  }


  fun finishRefresh(succeed: Boolean) {
    mSmartRefreshLayout.finishRefresh(succeed)
  }

}
