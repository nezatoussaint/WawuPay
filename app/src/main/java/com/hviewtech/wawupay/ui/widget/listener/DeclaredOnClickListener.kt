package com.hviewtech.wawupay.ui.widget.listener

import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View
import android.view.View.NO_ID
import com.hviewtech.wawupay.common.ext.loge
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * An implementation of OnClickListener that attempts to lazily load a
 * named click handling method from a parent or ancestor context.
 */
class DeclaredOnClickListener(@param:NonNull private val mHostView: View, @param:NonNull private val mMethodName: String) :
  View.OnClickListener {

  private var mResolvedMethod: Method? = null
  private var mResolvedContext: Context? = null

  override fun onClick(@NonNull v: View) {
    if (mResolvedMethod == null) {
      resolveMethod(mHostView.context, mMethodName)
    }
    try {
      if ("onBackPressed" == mMethodName) {
        mResolvedMethod?.invoke(mResolvedContext)
      } else {
        mResolvedMethod?.invoke(mResolvedContext, v)
      }
    } catch (e: IllegalAccessException) {
      loge(e, "Could not execute non-public method for app:app_backClick")
    } catch (e: InvocationTargetException) {
      loge(e, "Could not execute method for app:app_backClick")
    }

  }

  @NonNull
  private fun resolveMethod(@Nullable ctx: Context?, @NonNull name: String) {
    var context = ctx
    while (context != null) {
      try {


        if (!context.isRestricted) {

          val method: Method?
          if ("onBackPressed" == name) {
            method = context.javaClass.getMethod(name)
          } else {
            method = context.javaClass.getMethod(name, View::class.java)
          }

          if (method != null) {
            mResolvedMethod = method
            mResolvedContext = context
            return
          }
        }
      } catch (e: NoSuchMethodException) {
        // Failed to find method, keep searching up the hierarchy.
      }

      if (context is ContextWrapper) {
        context = context.baseContext
      } else {
        // Can't search up the hierarchy, null out and fail.
        context = null
      }
    }

    val id = mHostView.id
    val idText = if (id == NO_ID) ""
    else " with id '" + mHostView.context.resources.getResourceEntryName(id) + "'"
    loge(
      ("Could not find method " + mMethodName
          + "(View) in a parent or ancestor Context for app:app_backClick "
          + "attribute defined on view " + mHostView.javaClass + idText)
    )
  }
}
