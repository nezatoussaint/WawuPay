package com.hviewtech.wawupay.common.utils

import android.content.Context
import com.bumptech.glide.Glide

object GlideUtils {
  /**
   * 清除缓存
   *
   * @param context
   */
  fun clearCache(context: Context) {
    clearMemoryCache(context)
    Thread(Runnable { clearDiskCache(context) }).start()
  }

  /**
   * 清除内存缓存
   *
   * @param context
   */
  fun clearMemoryCache(context: Context) {
    Glide.get(context).clearMemory()
  }

  /**
   * 清除磁盘缓存
   *
   * @param context
   */
  fun clearDiskCache(context: Context) {
    Glide.get(context).clearDiskCache()
  }

}
