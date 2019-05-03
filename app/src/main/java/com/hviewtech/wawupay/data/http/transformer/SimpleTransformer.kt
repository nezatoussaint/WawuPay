package com.hviewtech.wawupay.data.http.transformer

import com.hviewtech.wawupay.common.utils.DeviceUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class SimpleTransformer<T>() : FlowableTransformer<T, T>, ObservableTransformer<T, T> {

  private var lifecycle: LifecycleTransformer<T>? = null

  constructor(lifecycle: LifecycleTransformer<T>?) : this() {
    this.lifecycle = lifecycle
  }

  override fun apply(upstream: Flowable<T>): Publisher<T> {
    val observable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.cancel()
      }
    }.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

    if (lifecycle != null) {
      return observable.compose(lifecycle)
    }
    return observable

  }

  override fun apply(upstream: Observable<T>): ObservableSource<T> {
    val observable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.dispose()
      }
    }.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

    if (lifecycle != null) {
      return observable.compose(lifecycle)
    }
    return observable

  }


  companion object {

    fun <T> create(lifecycle: LifecycleTransformer<T>? = null): SimpleTransformer<T> {
      return SimpleTransformer(lifecycle)
    }
  }
}