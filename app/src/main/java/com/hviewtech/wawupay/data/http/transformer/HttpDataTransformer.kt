package com.hviewtech.wawupay.data.http.transformer

import com.hviewtech.wawupay.bean.remote.BaseDataResult
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.DeviceUtils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class HttpDataTransformer<T>() : FlowableTransformer<BaseDataResult<T>, T>,
  ObservableTransformer<BaseDataResult<T>, T> {


  override fun apply(upstream: Flowable<BaseDataResult<T>>): Publisher<T> {
    val flowable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.cancel()
      }
    }
      .flatMap { result ->
        if (result.code == Constants.Status.OK) {

          Flowable.defer {
            Flowable.just(result.data?.data ?: Any() as T)
          }

        } else {
          Flowable.error<T>(Exception(result.msg))
        }
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())


    return flowable

  }

  override fun apply(upstream: Observable<BaseDataResult<T>>): ObservableSource<T> {
    val observable = upstream.doOnSubscribe {
      if (DeviceUtils.isWifiProxy()) {
        it.dispose()
      }
    }
      .flatMap { result ->
        if (result.code == Constants.Status.OK) {
          Observable.just(result.data?.data ?: Any() as T)
        } else {
          Observable.error<T>(Exception(result.msg))
        }
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())


    return observable
  }

}