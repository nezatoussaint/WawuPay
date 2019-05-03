package com.hviewtech.wawupay.data.http.transformer

import com.hviewtech.wawupay.bean.remote.BaseDataResult
import com.hviewtech.wawupay.bean.remote.Data
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
class HttpDataListTransformer<T>() : FlowableTransformer<BaseDataResult<T>?, Data<T>?>,
    ObservableTransformer<BaseDataResult<T>?, Data<T>?> {


    override fun apply(upstream: Flowable<BaseDataResult<T>?>): Publisher<Data<T>?> {
        val flowable = upstream.doOnSubscribe {
            if (DeviceUtils.isWifiProxy()) {
                it.cancel()
            }
        }
            .flatMap { result ->
                if (result.code == Constants.Status.OK) {

                    Flowable.defer {
                        Flowable.just(result.data ?: Any() as? Data<T>)
                    }

                } else {
                    Flowable.error<Data<T>>(Exception(result.msg))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


        return flowable

    }

    override fun apply(upstream: Observable<BaseDataResult<T>?>): ObservableSource<Data<T>?> {
        val observable = upstream.doOnSubscribe {
            if (DeviceUtils.isWifiProxy()) {
                it.dispose()
            }
        }
            .flatMap { result ->
                if (result.code == Constants.Status.OK) {
                    Observable.just(result.data ?: Any() as Data<T>)
                } else {
                    Observable.error<Data<T>>(Exception(result.msg))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


        return observable
    }

}