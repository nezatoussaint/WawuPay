package com.hviewtech.wawupay.common.utils

import android.content.Context
import android.net.ConnectivityManager
import com.hviewtech.wawupay.common.Constants


object NetUtils {
  /**
   * 判断网络是否连接
   * @param context
   * @return
   */
  fun isConnected(context: Context): Boolean {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (null != connectivity) {
      val info = connectivity.activeNetworkInfo
      if (null != info && info.isAvailable) {
        return true
      }
    }

    return false
  }


  fun ping() {
    val runtime = Runtime.getRuntime()
    val process = runtime.exec("ping -c 3 ${Constants.URL.HOST}")
  }

}