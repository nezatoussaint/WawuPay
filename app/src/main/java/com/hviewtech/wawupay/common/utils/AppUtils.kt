package com.hviewtech.wawupay.common.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.InputFilter
import android.view.Gravity
import android.webkit.WebView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import java.io.File
import java.util.*


object AppUtils {

  /* 1.如果包名不存在 就会报错  return -1
  System.err:     android.content.pm.PackageManager$NameNotFoundException: [配置包名]
  System.err:     at android.app.ApplicationPackageManager.getPackageInfo(ApplicationPackageManager.java:137)

   2.如果配置包名不是当前进程的包名 那么，也不会报错。
      这点我觉得奇怪 居然可以取得别人的包的keystore的hash
  */
  fun getSignature(context: Context): String {

    try {
      val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)

      val signs = packageInfo.signatures
      val sign = signs[0]
      return sign.hashCode().toString()
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return ""
  }


  fun addBGChild(frameLayout: FrameLayout) {
    val mTextView = TextView(frameLayout.context)
    mTextView.text = "技术由 钱搭档 提供"
    mTextView.textSize = 16f
    mTextView.setTextColor(Color.parseColor("#727779"))
    frameLayout.setBackgroundColor(Color.parseColor("#272b2d"))
    val mFlp = FrameLayout.LayoutParams(-2, -2)
    mFlp.gravity = Gravity.CENTER_HORIZONTAL
    val scale = frameLayout.context.resources.displayMetrics.density
    mFlp.topMargin = (15 * scale + 0.5f).toInt()
    frameLayout.addView(mTextView, 0, mFlp)
  }


  fun captureWebView(webView: WebView?): Bitmap? {
    webView ?: return null

    val picture = webView.capturePicture()
    val width = picture.getWidth()
    val height = picture.getHeight()
    if (width > 0 && height > 0) {
      val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
      val canvas = Canvas(bitmap)
      picture.draw(canvas)
      return bitmap
    }
    return null
  }


  fun shareImage(context: Context, imagePath: String?) {
    imagePath ?: return
    //由文件得到uri
    val imageUri = Uri.fromFile(File(imagePath))

    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    shareIntent.type = "image/*"
    context.startActivity(Intent.createChooser(shareIntent, "分享到"))
  }


  /**
   * 保留原有Filter,同时新增默认的过滤规则
   *
   * @param editText edittext
   */
  fun setEditTextInhibitInputSpace(editText: EditText, newFilter: InputFilter) {

    //保留原有的exittext的filter
    val oldFilters = editText.filters
    val oldFiltersLength = oldFilters.size
    val newFilters = arrayOfNulls<InputFilter>(oldFiltersLength + 1)
    if (oldFiltersLength > 0) {
      System.arraycopy(oldFilters, 0, newFilters, 0, oldFiltersLength)
    }
    //添加新的过滤规则
    newFilters[oldFiltersLength] = newFilter
    editText.filters = newFilters
  }

  /**
   * 切换应用语言环境
   *
   * @param context
   */
  fun loadAppLanguage(context: Context?, targtLocal: Locale) {
    // 模拟器无法跟随指定语言环境
    if (context == null) {
      throw NullPointerException("loadAppLanguage failed because context is null")
    }
    val config = context.resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      config.setLocale(targtLocal)
    } else {
      config.locale = targtLocal
    }
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    Locale.setDefault(targtLocal)
  }

}