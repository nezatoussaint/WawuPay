package com.hviewtech.wawupay.data.http.transformer

import com.hviewtech.wawupay.common.utils.DeviceUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class EmptyTransformer<T>() : FlowableTransformer<T, T>, ObservableTransformer<T, T> {

  private var lifecycle: LifecycleTransformer<T>? = null

  constructor(lifecycle: LifecycleTransformer<T>?) : this() {
    this.lifecycle = lifecycle
  }

  override fun apply(upstream: Flowable<T>): Publisher<T> =
    upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.cancel()
      }
    }.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

  override fun apply(upstream: Observable<T>): ObservableSource<T> =
    upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
      }
    }.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

}