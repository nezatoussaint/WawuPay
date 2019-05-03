package com.hviewtech.wawupay.data.http.subscriber

import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.contract.Contract
import org.reactivestreams.Subscription

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class SimpleSubscriber<T>() : EmptySubscriber<T>() {

  private var mView: Contract.View? = null

  constructor(view: Contract.View?) : this() {
    mView = view
  }

  override fun onSubscribe(s: Subscription) {
    super.onSubscribe(s)
    s.request(Long.MAX_VALUE)
    mView?.showloading()

  }

  override fun onComplete() {
    super.onComplete()
    mView?.dismissloading()
  }


  override fun onNext(data: T) {
    super.onNext(data)
  }

  override fun onError(e: Throwable) {
    super.onError(e)
    mView?.dismissloading(true)
    toast(e.message)
  }
}