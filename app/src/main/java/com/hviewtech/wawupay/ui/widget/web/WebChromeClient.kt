package com.android.moneypartners.jsbridge.client

import android.text.TextUtils
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.hviewtech.wawupay.common.ext.logd
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.ui.widget.TitleView

class WebChromeClient(val webTitle: TitleView?) : WebChromeClient() {
  override fun onProgressChanged(view: WebView, newProgress: Int) {
    //do you work
    logd("onProgress:" + newProgress);
  }

  override fun onReceivedTitle(view: WebView, title: String?) {
    super.onReceivedTitle(view, title)
    var title = if (title == null) "" else title
    if (!TextUtils.isEmpty(title)) run({
      if (title.length > 10) {
        title = title.substring(0, 10) + "..."
      }
    })
    webTitle?.title = title
  }

  override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
    toast(message)
    result?.confirm()
    return true
  }

}