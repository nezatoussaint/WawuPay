package com.hviewtech.wawupay.common.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.EditText
import android.widget.TextView
import com.hviewtech.wawupay.ui.activity.account.LoginActivity
import com.luck.picture.lib.tools.PictureFileUtils

object ActivityUtils {

  /**
   * 隐藏
   */
  private fun hideNavigationBar(activity: Activity) {
    val decorView = activity.window.decorView
    val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        // android4.2 touch了也不会显示NavigationBar
        or View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_FULLSCREEN)
    decorView.systemUiVisibility = uiOptions
  }

  private fun getNavigationBarHeight(context: Context?): Int {
    if (context == null) {
      return 0
    }

    val resources = context.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
    //        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
    //        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
    //        if (!hasMenuKey && !hasBackKey) {
    //            Resources resources = context.getResources();
    //            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
    //            //获取NavigationBar的高度
    //            int height = resources.getDimensionPixelSize(resourceId);
    //            return height;
    //        } else {
    //            return 0;
    //        }

  }

  private fun isNavigationBarShow(activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      // 红米全面屏  经典导航  两种模式
      val status = Settings.Global.getInt(activity.contentResolver, "force_fsg_nav_bar", 0)
      if (status == 1) {
        // 红米全面屏操作模式
        return false
      }
      val display = activity.windowManager.defaultDisplay
      val size = Point()
      val realSize = Point()
      display.getSize(size)
      display.getRealSize(realSize)
      return realSize.y != size.y || realSize.x != size.x
    } else {
      val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
      val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
      return if (menu || back) {
        false
      } else {
        true
      }
    }
  }

  fun adaptNavigationBar(activity: Activity?) {
    if (activity == null) {
      return
    }
    if (isNavigationBarShow(activity)) {
      activity.window.decorView.findViewById<View>(android.R.id.content)
        .setPadding(0, 0, 0, getNavigationBarHeight(activity))
    } else {
      activity.window.decorView.findViewById<View>(android.R.id.content).setPadding(0, 0, 0, 0)
    }
  }

  fun getActivity(context: Context?): Activity? {
    var context: Context? = context ?: return null
    while (context is ContextWrapper) {
      if (context is Activity) {
        return context
      }
      context = context.baseContext
    }
    return null
  }


  fun toReLogin(context: Context?) {
    if (context == null) {
      return
    }
    // 清除缓存
    HawkExt.clear()
    //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
    PictureFileUtils.deleteCacheDirFile(context)
    GlideUtils.clearCache(context)
    val intent = Intent(context, LoginActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)


  }


  fun setSelection(editText: EditText?, text: String?) {
    if (editText == null || text == null) {
      return
    }
    editText.setTextKeepState(text, TextView.BufferType.EDITABLE)
    editText.setSelection(text.length)
  }


  /**
   * The `fragment` is added to the container view with id `frameId`. The operation is
   * performed by the `fragmentManager`.
   */
  fun addFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment, frameId: Int
  ) {
    checkNotNull(fragmentManager)
    checkNotNull(fragment)
    val transaction = fragmentManager.beginTransaction()
    transaction.add(frameId, fragment)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      transaction.commitNow()
    } else {
      transaction.commit()
    }
  }

  /**
   * The `fragment` is added to the container view with id `frameId`. The operation is
   * performed by the `fragmentManager`.
   */
  fun showFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment
  ) {
    checkNotNull(fragmentManager)
    checkNotNull(fragment)
    val transaction = fragmentManager.beginTransaction()
    transaction.show(fragment)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      transaction.commitNow()
    } else {
      transaction.commit()
    }
  }

}