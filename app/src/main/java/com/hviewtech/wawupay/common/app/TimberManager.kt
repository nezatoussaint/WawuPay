package com.hviewtech.wawupay.common.app

import android.util.Log
import com.hviewtech.wawupay.BuildConfig
import com.hviewtech.wawupay.common.utils.Timber

/**
 * @author 诸葛不亮
 * @version 1.0
 * @description log管理器，打印日志
 */

object TimberManager {
  fun initialize() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      // 非debug模式仅打印崩溃日志
      Timber.plant(CrashReportingTree())
    }
  }

  /**
   * A tree which logs important information for crash reporting.
   */
  private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return
      }

      FakeCrashLibrary.log(priority, tag, message)

      if (t != null) {
        if (priority == Log.ERROR) {
          FakeCrashLibrary.logError(tag, t)
          w(t)
        } else if (priority == Log.WARN) {
          FakeCrashLibrary.logWarning(tag, t)
          w(t)
        }
      }
    }
  }

  class FakeCrashLibrary private constructor() {
    init {
      throw AssertionError("No instances.")
    }

    companion object {
      val MAX_LOG_LENGTH = 4000
      fun log(priority: Int, tag: String?, message: String) {
        if (message.length < MAX_LOG_LENGTH) {
          if (priority == Log.ASSERT) {
            Log.wtf(tag, message)
          } else {
            Log.println(priority, tag, message)
          }
          return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
          var newline = message.indexOf('\n', i)
          newline = if (newline != -1) newline else length
          do {
            val end = Math.min(newline, i + MAX_LOG_LENGTH)
            val part = message.substring(i, end)
            if (priority == Log.ASSERT) {
              Log.wtf(tag, part)
            } else {
              Log.println(priority, tag, part)
            }
            i = end
          } while (i < newline)
          i++
        }
      }

      fun logWarning(tag: String?, t: Throwable) {
        Log.w(tag, t)
      }

      fun logError(tag: String?, t: Throwable) {
        Log.wtf(tag, t)
      }
    }
  }

}