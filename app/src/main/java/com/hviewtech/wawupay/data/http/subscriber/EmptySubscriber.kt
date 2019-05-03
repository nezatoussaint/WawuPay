package com.hviewtech.wawupay.data.http.subscriber

import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.data.http.exception.ErrorHandler
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class EmptySubscriber<T> : Subscriber<T> {
  override fun onComplete() {
  }

  override fun onSubscribe(s: Subscription) {
  }

  override fun onNext(data: T) {

  }

  override fun onError(e: Throwable) {

    val exception = ErrorHandler.analysisError(e)
    if (exception.code == Constants.Status.TOKEN_INVALID) {
      toast(exception.displayMessage)
      ActivityUtils.toReLogin(AppApplication.app)
      return
    }
    onError(exception.code, exception.displayMessage)
  }

  fun onError(code: Int, msg: String?) {}
}