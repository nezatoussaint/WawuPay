package com.hviewtech.wawupay.data.http.transformer

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.DeviceUtils
import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class DataTransformer<T>() : FlowableTransformer<BaseResult<T>, T>, ObservableTransformer<BaseResult<T>, T> {


  override fun apply(upstream: Flowable<BaseResult<T>>): Publisher<T> {
    val flowable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.cancel()
      }
    }
      .flatMap { result ->
        if (result.code == Constants.Status.OK) {

          Flowable.defer {
            Flowable.just(result.data ?: Any() as T)
          }

        } else {
          Flowable.error<T>(Exception(result.msg))
        }
      }


    return flowable

  }

  override fun apply(upstream: Observable<BaseResult<T>>): ObservableSource<T> {
    val observable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.dispose()
      }
    }
      .flatMap { result ->
        if (result.code == Constants.Status.OK) {
          Observable.just(result.data ?: Any() as T)
        } else {
          Observable.error<T>(Exception(result.msg))
        }
      }


    return observable
  }

}