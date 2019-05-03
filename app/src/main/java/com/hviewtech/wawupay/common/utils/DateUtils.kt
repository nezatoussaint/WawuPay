/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.hviewtech.wawupay.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by h4de5ing 2016/5/7 007
 */
object DateUtils {
  private val DATE_FORMAT_DATETIME = SimpleDateFormat("yyyy-M-d HH:mm", Locale.US)
  private val DATE_FORMAT_DATE = SimpleDateFormat("MMM-dd-yy", Locale.US)
  private val DATE_FORMAT_DATE2 = SimpleDateFormat("MMM-dd-yyyy", Locale.US)
  private val DATE_FORMAT_TIME = SimpleDateFormat("HH:mm:ss", Locale.US)
  private val DATE_FORMAT_SHORT_TIME = SimpleDateFormat("HH:mm", Locale.US)

  private val DATE_FORMAT_SHORT_TIME2 = SimpleDateFormat("hh:mm a", Locale.US)

  val time: String
    get() {
      val cal = Calendar.getInstance()
      cal.timeInMillis = System.currentTimeMillis()
      return cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND)
    }

  val date: String
    get() = SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis())

  val dateTime: String
    get() = DATE_FORMAT_DATETIME.format(System.currentTimeMillis())

  val weekOfMonth: Int
    get() {
      val calendar = Calendar.getInstance()
      val week = calendar.get(Calendar.WEEK_OF_MONTH)
      return week - 1
    }

  val dayOfWeek: Int
    get() {
      val calendar = Calendar.getInstance()
      var day = calendar.get(Calendar.DAY_OF_WEEK)
      if (day == 1) {
        day = 7
      } else {
        day = day - 1
      }
      return day
    }

  fun formatDataTime(date: Long): String {
    return DATE_FORMAT_DATETIME.format(Date(date))
  }

  fun formatDate(date: Long): String {
    return DATE_FORMAT_DATE.format(Date(date))
  }

  fun formatDate2(date: Long): String {
    return DATE_FORMAT_DATE2.format(Date(date))
  }

  fun formatTime(date: Long): String {
    return DATE_FORMAT_TIME.format(Date(date))
  }

  fun formatShortTime(date: Long): String {
    return DATE_FORMAT_SHORT_TIME.format(Date(date))
  }

  fun formatShortTime2(date: Long): String {
    return DATE_FORMAT_SHORT_TIME2.format(Date(date))
  }

  fun formatDateCustom(beginDate: String, format: String): String {
    return SimpleDateFormat(format).format(Date(java.lang.Long.parseLong(beginDate)))
  }

  fun formatDateCustom(beginDate: Date, format: String): String {
    return SimpleDateFormat(format).format(beginDate)
  }

  fun string2Date(s: String?, style: String): Date? {
    val simpleDateFormat = SimpleDateFormat()
    simpleDateFormat.applyPattern(style)
    var date: Date? = null
    if (s == null || s.length < 6) {
      return null
    }
    try {
      date = simpleDateFormat.parse(s)
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    return date
  }

  fun getDateTime(format: String): String {
    return SimpleDateFormat(format).format(System.currentTimeMillis())
  }

  fun subtractDate(dateStart: Date, dateEnd: Date): Long {
    return dateEnd.time - dateStart.time
  }

  fun getDateAfter(d: Date, day: Int): Date {
    val now = Calendar.getInstance()
    now.time = d
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day)
    return now.time
  }
}
