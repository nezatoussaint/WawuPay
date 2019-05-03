package com.hviewtech.wawupay.common.ext


import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.Constants


fun toast(content: CharSequence?) {

  Constants.App.TOAST?.apply {
    cancel()
    showToast(content)
  } ?: run {
    showToast(content)
  }
}

fun toast(content: Int) {

  Constants.App.TOAST?.apply {
    cancel()
    showToast(content)
  } ?: run {
    showToast(content)
  }
}


private fun showToast(content: Any?) {
  content ?: return
  if (content is CharSequence) {
    Toast.makeText(AppApplication.app, content, Toast.LENGTH_SHORT).apply {
      view = View.inflate(AppApplication.app, R.layout.toast_layout, null)
      view.findViewById<TextView>(R.id.toastMessage).setText(content)
      Constants.App.TOAST = this
    }.show()

  } else if (content is Int) {
    Toast.makeText(AppApplication.app, content, Toast.LENGTH_SHORT).apply {
      view = View.inflate(AppApplication.app, R.layout.toast_layout, null)
      view.findViewById<TextView>(R.id.toastMessage).setText(content)
      Constants.App.TOAST = this
    }.show()
  }

}

