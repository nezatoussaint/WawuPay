package com.hviewtech.wawupay.ui.activity.common

import android.view.KeyEvent
import android.widget.LinearLayout
import com.android.moneypartners.jsbridge.client.WebChromeClient
import com.android.moneypartners.jsbridge.client.WebViewClient
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.ui.widget.web.AgentWebSettings
import com.hviewtech.wawupay.ui.widget.web.WebLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.act_web.*

class WebActivity : BaseActivity() {
  companion object {
    val URL = "url"
  }

  private var mAgentWeb: AgentWeb? = null

  override fun getLayoutId(): Int {
    return R.layout.act_web
  }


  override fun initialize() {
    super.initialize()

    val webLayout = WebLayout(this)

    val agentWeb = AgentWeb.with(this)
      .setAgentWebParent(webParent, LinearLayout.LayoutParams(-1, -1))
      .useDefaultIndicator(resources.getColor(R.color.colorPrimary), 1)
      .setWebLayout(webLayout)
      .setAgentWebWebSettings(AgentWebSettings())
      .setWebChromeClient(WebChromeClient(titleView))
      .setWebViewClient(WebViewClient(titleView))
      .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
      .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
      .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW) //打开其他页面时，不允许质询用户前往其他应用
      .interceptUnkownUrl() //拦截找不到相关页面的Scheme
      .createAgentWeb()
      .ready()

    val url = intent.getStringExtra(URL)
    if (url.isNullOrBlank()) {
      finish()
      return
    }
    mAgentWeb = agentWeb.go(url)
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
    return if (mAgentWeb != null && mAgentWeb!!.handleKeyEvent(keyCode, event)) {
      true
    } else {
      super.onKeyDown(keyCode, event)
    }
  }

  override fun onPause() {
    mAgentWeb?.webLifeCycle?.onPause()
    super.onPause()

  }

  override fun onResume() {
    mAgentWeb?.webLifeCycle?.onResume()
    super.onResume()
  }

  override fun onDestroy() {
    mAgentWeb?.webLifeCycle?.onDestroy()
    super.onDestroy()
  }


  override fun onBackPressed() {
    return if (mAgentWeb != null && mAgentWeb!!.back()) {
    } else {
      super.onBackPressed()
    }
  }

}