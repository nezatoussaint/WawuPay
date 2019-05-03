package com.hviewtech.wawupay.common.ext

import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.common.utils.Timber

/**
 * Log
 */
fun Any.logd(message: Any?, vararg data: Any?) {
  if (message is String) {
    Timber.d(message, *data)
  } else if (message is Throwable) {
    Timber.d(message)
  } else {
    Timber.d(JsonUtils.toJson(message))
  }
}

fun Any.loge(message: Any?, vararg data: Any?) {
  if (message is String) {
    Timber.e(message, *data)
  } else if (message is Throwable) {
    Timber.e(message, "%s", *data)
  } else {
    Timber.e(JsonUtils.toJson(message))
  }
}
