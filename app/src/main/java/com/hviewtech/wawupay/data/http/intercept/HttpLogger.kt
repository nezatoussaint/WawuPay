package com.hviewtech.wawupay.data.http.intercept

import com.hviewtech.wawupay.common.ext.logd
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    logd(message)
  }
}