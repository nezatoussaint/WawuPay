/*
 * Copyright (C)  Justson(https://github.com/Justson/AgentWeb)
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

package com.hviewtech.wawupay.ui.widget.web


import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.just.agentweb.*


/**
 * @author cenxiaozhong
 * @update 4.0.0 ,WebDefaultSettingsManager rename to AgentWebSettings
 * @since 1.0.0
 */

class AgentWebSettings() : AbsAgentWebSettings() {

  override fun bindAgentWebSupport(agentWeb: AgentWeb?) {
    mAgentWeb = agentWeb
  }

  private var mWebSettings: WebSettings? = null


  internal fun bindAgentWeb(agentWeb: AgentWeb) {
    this.mAgentWeb = agentWeb
    this.bindAgentWebSupport(agentWeb)

  }

  override fun toSetting(webView: WebView): IAgentWebSettings<*> {
    settings(webView)
    return this
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun settings(webView: WebView) {

    mWebSettings = webView.settings
    mWebSettings!!.javaScriptEnabled = true
    mWebSettings!!.setSupportZoom(false)
    mWebSettings!!.builtInZoomControls = false
    mWebSettings!!.savePassword = false
    if (AgentWebUtils.checkNetwork(webView.context)) {
      //根据cache-control获取数据。
      mWebSettings!!.cacheMode = WebSettings.LOAD_DEFAULT
    } else {
      //没网，则从本地获取，即离线加载
      mWebSettings!!.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //适配5.0不允许http和https混合使用情况
      mWebSettings!!.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
      webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    mWebSettings!!.textZoom = 100
    mWebSettings!!.databaseEnabled = true
    mWebSettings!!.setAppCacheEnabled(true)
    mWebSettings!!.loadsImagesAutomatically = true
    mWebSettings!!.setSupportMultipleWindows(false)
    // 是否阻塞加载网络图片  协议http or https
    mWebSettings!!.blockNetworkImage = false
    // 允许加载本地文件html  file协议
    mWebSettings!!.allowFileAccess = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
      mWebSettings!!.allowFileAccessFromFileURLs = false
      // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
      mWebSettings!!.allowUniversalAccessFromFileURLs = false
    }
    mWebSettings!!.javaScriptCanOpenWindowsAutomatically = false

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      mWebSettings!!.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    } else {
      mWebSettings!!.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
    }
    mWebSettings!!.loadWithOverviewMode = false
    mWebSettings!!.useWideViewPort = true
    mWebSettings!!.domStorageEnabled = true
    mWebSettings!!.setNeedInitialFocus(true)
    mWebSettings!!.defaultTextEncodingName = "utf-8" //设置编码格式
    mWebSettings!!.defaultFontSize = 16
    mWebSettings!!.minimumFontSize = 12 //设置 WebView 支持的最小字体大小，默认为 8
    mWebSettings!!.setGeolocationEnabled(true)

    //
    val dir = AgentWebConfig.getCachePath(webView.context)

    //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
      mWebSettings!!.setGeolocationDatabasePath(dir)
      mWebSettings!!.databasePath = dir
    }
    mWebSettings!!.setAppCachePath(dir)

    //缓存文件最大值
    //    mWebSettings!!.setAppCacheMaxSize(Long.MAX_VALUE)
    //    mWebSettings!!.userAgentString = webSettings!!.userAgentString
  }
}
