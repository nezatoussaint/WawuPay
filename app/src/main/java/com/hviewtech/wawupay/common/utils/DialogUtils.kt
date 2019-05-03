package com.hviewtech.wawupay.common.utils

import android.content.Context
import cn.pedant.sweetalert.ProgressDialog
import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.R
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.SoftReference
import java.util.concurrent.TimeUnit

/**
 * @author su
 * @date 2018/3/24
 * @description
 */

object DialogUtils {

  private var sLoadingDisposable: Disposable? = null
  private var sDismissLoadingDisposable: Disposable? = null
  private var reference: SoftReference<ProgressDialog>? = null
  private var sLoadingCount = 0

  fun showProgressLoading(context: Context, transformer: ObservableTransformer<in Long, Any>) {
    if (sLoadingDisposable != null || reference?.get() != null) {
      return
    }
    sLoadingCount = 0
    val dialog = ProgressDialog(context, ProgressDialog.PROGRESS_TYPE)
      .setTitleText("Loading")

    reference = SoftReference<ProgressDialog>(dialog)

    dialog.show()
    dialog.setCancelable(false)
    sLoadingDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
      .compose<Any>(transformer)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { o ->
        val resources = AppApplication.app.resources
        sLoadingCount++
        when (sLoadingCount) {
          0 -> dialog.progressHelper.barColor = resources.getColor(R.color.blue_btn_bg_color)
          1 -> dialog.progressHelper.barColor = resources.getColor(R.color.material_deep_teal_50)
          2 -> dialog.progressHelper.barColor = resources.getColor(R.color.success_stroke_color)
          3 -> dialog.progressHelper.barColor = resources.getColor(R.color.material_deep_teal_20)
          4 -> dialog.progressHelper.barColor = resources.getColor(R.color.material_blue_grey_80)
          5 -> dialog.progressHelper.barColor = resources.getColor(R.color.warning_stroke_color)
          6 -> dialog.progressHelper.barColor = resources.getColor(R.color.success_stroke_color)
        }
      }
  }

  fun dismissProgressLoading(transformer: ObservableTransformer<in Long, *>, func: () -> Unit = {}) {
    val dialog = reference?.get()
    if (dialog == null) {
      return
    }
    sLoadingDisposable?.dispose()
    sLoadingDisposable = null
    dialog.setTitleText("Succeed!")
      .setCustomImage(R.drawable.register_success)
      .changeAlertType(ProgressDialog.CUSTOM_IMAGE_TYPE)
    sDismissLoadingDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
      .doOnDispose {
        dialog.dismiss()
        reference?.clear()
      }
      .compose<Any>(transformer)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { o ->
        sDismissLoadingDisposable?.dispose()
        sDismissLoadingDisposable = null
        dialog.dismiss()
        reference?.clear()
        func.invoke()
      }
  }

  fun dismissErrorLoading(transformer: ObservableTransformer<in Long, *>, func: () -> Unit = {}) {
    val dialog = reference?.get()
    if (dialog == null) {
      return
    }
    sLoadingDisposable?.dispose()
    sLoadingDisposable = null
    dialog.progressHelper.barColor = 0x00ffffff
    dialog.setTitleText("Error!")
      .setCustomImage(R.drawable.error_center_x)
      .changeAlertType(ProgressDialog.CUSTOM_IMAGE_TYPE)
    sDismissLoadingDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
      .doOnDispose {
        dialog.dismiss()
        reference?.clear()
      }
      .compose<Any>(transformer)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { o ->
        sDismissLoadingDisposable?.dispose()
        sDismissLoadingDisposable = null
        dialog.dismiss()
        reference?.clear()
        func.invoke()

      }
  }

  fun dismissProgressLoading() {
    sLoadingDisposable?.dispose()
    sLoadingDisposable = null
    val dialog = reference?.get()
    if (dialog != null) {
      dialog.dismiss()
      reference?.clear()
    }

  }
}
