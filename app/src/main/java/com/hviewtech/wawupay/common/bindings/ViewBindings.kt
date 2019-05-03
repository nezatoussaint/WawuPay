package com.hviewtech.wawupay.common.bindings

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

object ViewBindings {

  fun click(view: View, onNext: (Any) -> Unit = {}) {
    RxView.clicks(view)
      .throttleFirst(1, TimeUnit.SECONDS)
      .subscribe(onNext)
  }

  fun longClick(view: View, onNext: (Any) -> Unit = {}) {
    RxView.longClicks(view)
      .throttleFirst(1, TimeUnit.SECONDS)
      .subscribe(onNext)
  }
}
