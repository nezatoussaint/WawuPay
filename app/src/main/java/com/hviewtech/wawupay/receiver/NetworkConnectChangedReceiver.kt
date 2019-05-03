package com.hviewtech.wawupay.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.hviewtech.wawupay.bean.local.Network
import com.hviewtech.wawupay.common.ext.logd
import com.hviewtech.wawupay.common.utils.NetUtils
import com.blankj.rxbus.RxBus

class NetworkConnectChangedReceiver : BroadcastReceiver() {

  companion object {
    var lastState: Boolean? = null
  }

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION
      || intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION
      || intent.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
      //**判断当前的网络连接状态是否可用*/
      val isConnected = NetUtils.isConnected(context)
      val extras = intent.extras

      if (lastState != isConnected) {
        RxBus.getDefault().postSticky(Network(isConnected))
        lastState = isConnected
      }
      logd("onReceive: 当前网络 $isConnected")
    }
  }
}