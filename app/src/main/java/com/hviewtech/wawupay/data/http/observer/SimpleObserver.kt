/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.hviewtech.wawupay.data.http.observer

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.util.EndConsumerHelper

/**
 * Abstract base implementation of an [Observer][io.reactivex.Observer] with support for cancelling a
 * subscription via [.cancel] (synchronously) and calls [.onStart]
 * when the subscription happens.
 *
 *
 * All pre-implemented final methods are thread-safe.
 *
 *
 * Use the protected [.cancel] to dispose the sequence from within an
 * `onNext` implementation.
 *
 *
 * Like all other consumers, `DefaultObserver` can be subscribed only once.
 * Any subsequent attempt to subscribe it to a new source will yield an
 * [IllegalStateException] with message `"It is not allowed to subscribe with a(n) <class name> multiple times."`.
 *
 *
 * Implementation of [.onStart], [.onNext], [.onError]
 * and [.onComplete] are not allowed to throw any unchecked exceptions.
 * If for some reason this can't be avoided, use [io.reactivex.Observable.safeSubscribe]
 * instead of the standard `subscribe()` method.
 *
 *
 * Example<pre>`
 * Observable.range(1, 5)
 * .subscribe(new DefaultObserver<Integer>() {
 * &#64;Override public void onStart() {
 * System.out.println("Start!");
 * }
 * &#64;Override public void onNext(Integer t) {
 * if (t == 3) {
 * cancel();
 * }
 * System.out.println(t);
 * }
 * &#64;Override public void onError(Throwable t) {
 * t.printStackTrace();
 * }
 * &#64;Override public void onComplete() {
 * System.out.println("Done!");
 * }
 * });
`</pre> *
 *
 * @param <T> the value type
</T> */
abstract class SimpleObserver<T> : Observer<T> {

  private var upstream: Disposable? = null

  override fun onSubscribe(@NonNull d: Disposable) {
    if (EndConsumerHelper.validate(this.upstream, d, javaClass)) {
      this.upstream = d
      onStart()
    }
  }

  /**
   * Cancels the upstream's disposable.
   */
  protected fun cancel() {
    val upstream = this.upstream
    this.upstream = DisposableHelper.DISPOSED
    upstream!!.dispose()
  }

  /**
   * Called once the subscription has been set on this observer; override this
   * to perform initialization.
   */
  protected fun onStart() {}


  override fun onError(e: Throwable) {
  }
}
