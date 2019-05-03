package com.hviewtech.wawupay.common.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
//打开或关闭软键盘
object KeyBoardUtils {

  private val imm: InputMethodManager? = null

  fun showKeyboard(dialog: Dialog?) {
    if (dialog != null) {
      val window = dialog.window
      if (window != null) {
        val view = window.peekDecorView()
        if (view != null) {
          val inputmanger = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
          inputmanger?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
      }
    }
  }

  fun showKeyboard(activity: Activity?) {
    if (activity != null) {
      val window = activity.window
      if (window != null) {
        val view = window.peekDecorView()
        if (view != null) {
          val inputmanger = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
          inputmanger?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
      }
    }
  }

  fun closeKeyboard(dialog: Dialog?) {
    if (dialog != null) {
      val window = dialog.window
      if (window != null) {
        val view = window.peekDecorView()
        if (view != null) {
          val inputmanger = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
          inputmanger?.hideSoftInputFromWindow(view.windowToken, 0)
        }
      }
    }
  }

  fun closeKeyboard(activity: Activity?) {
    if (activity != null) {
      val window = activity.window
      if (window != null) {
        val view = window.peekDecorView()
        if (view != null) {
          val inputmanger = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
          inputmanger?.hideSoftInputFromWindow(view.windowToken, 0)
        }
      }
    }
  }
}