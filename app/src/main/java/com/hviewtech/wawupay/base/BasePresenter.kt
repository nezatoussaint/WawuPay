package com.hviewtech.wawupay.base

import com.hviewtech.wawupay.contract.Contract
import com.hviewtech.wawupay.data.http.transformer.lifecycle.ViewEvent
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class BasePresenter<V : Contract.View> : Contract.Presenter, LifecycleProvider<ViewEvent> {

  protected var mView: V? = null

  protected val lifecycle = BehaviorSubject.create<ViewEvent>()

  @Suppress("UNCHECKED_CAST")
  override fun attachView(view: Contract.View) {
    lifecycle.onNext(ViewEvent.ATTACH)
    mView = view as? V
  }

  override fun detachView() {

    lifecycle.onNext(ViewEvent.DETACH)
    mView = null
  }

  override fun destroy() {
  }

  override fun lifecycle(): Observable<ViewEvent> {
    return lifecycle.hide()
  }

  override fun <T : Any?> bindUntilEvent(event: ViewEvent): LifecycleTransformer<T> {
    return RxLifecycle.bindUntilEvent(lifecycle, event)
  }

  override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
    return RxLifecycle.bindUntilEvent(lifecycle, ViewEvent.DETACH)
  }
}