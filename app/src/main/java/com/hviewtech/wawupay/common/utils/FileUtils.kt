package com.hviewtech.wawupay.common.utils

import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {


  /**
   * 将图片存到本地 (可在主线程)
   */
  fun saveBitmap(bm: Bitmap, imagePath: String): Uri? {
    try {
      val f = File(imagePath)
      val out = FileOutputStream(f, false)
      bm.compress(Bitmap.CompressFormat.PNG, 90, out)
      out.flush()
      out.close()
      return Uri.fromFile(f)
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return null
  }

}