package com.hviewtech.wawupay.common.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

object RxUtils {

  fun countDown(countDown: Int): Observable<Int> {
    var time = countDown
    if (time < 0) time = 0

    val countTime = time
    return Observable.interval(0, 1, TimeUnit.SECONDS)
      .subscribeOn(AndroidSchedulers.mainThread())
      .observeOn(AndroidSchedulers.mainThread())
      .map(object : Function<Long, Int> {
        override fun apply(increaseTime: Long): Int {
          return countTime - increaseTime.toInt()
        }
      })
      .take((countTime + 1).toLong());


  }

  fun delay(countDown: Int): Observable<Int> {
    var time = countDown
    if (time < 0) time = 0
    return Observable.timer(time.toLong(), TimeUnit.SECONDS)
      .subscribeOn(AndroidSchedulers.mainThread())
      .observeOn(AndroidSchedulers.mainThread())
      .map(object : Function<Long, Int> {
        @Throws(Exception::class)
        override fun apply(time: Long): Int {
          return time.toInt()
        }
      })

  }
}