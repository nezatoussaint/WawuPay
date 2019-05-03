package com.android.moneypartners.jsbridge.client

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.hviewtech.wawupay.ui.widget.TitleView


class WebViewClient(val webTitle: TitleView?) : android.webkit.WebViewClient() {

  private val deliver = Handler(Looper.getMainLooper())

  override fun onPageFinished(view: WebView, url: String?) {
    super.onPageFinished(view, url)
//    if (view.canGoBack()) {
//      webTitle?.setNavigation(true)
//    } else {
//      webTitle?.setNavigation(false)
//    }
  }



}