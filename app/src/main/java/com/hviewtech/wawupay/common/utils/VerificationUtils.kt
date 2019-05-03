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

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 * Create by h4de5ing 2016/5/21 021
 * https://github.com/sharinghuang/ASRabbit/blob/7350ea1c212946633316d36760c7088728dc2730/baselib/src/main/java/com/ht/baselib/utils/FormatVerificationUtils.java
 */
object VerificationUtils {
  fun matcherRealName(value: String): Boolean {
    val regex = "^([\\u4e00-\\u9fa5]+|([a-zA-Z]+\\s?)+)$"
    return testRegex(regex, value)
  }

  fun matcherPhoneNum(value: String): Boolean {
    val regex = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    return testRegex(regex, value)
  }

  fun matcherAccount(value: String): Boolean {
    val regex = "[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{4,20}"
    return testRegex(regex, value)
  }

  fun matcherPassword(value: String): Boolean {
    val regex = "^[a-zA-Z0-9]{6,12}$"
    return testRegex(regex, value)
  }

  fun matcherPassword2(value: String): Boolean {
    val regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}"
    return testRegex(regex, value)
  }


  fun matcherEmail(value: String?): Boolean {
    //      String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)" +
    //                "+[a-zA-Z]{2,}$";
    val regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
        "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
        "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    return testRegex(regex, value)
  }

  fun matcherIP(value: String): Boolean {
    val regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\" +
        "d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\" +
        "d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b"
    return testRegex(regex, value.toLowerCase())
  }

  fun matcherUrl(value: String): Boolean {
    //String regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";
    val regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[\\w-]+\\.\\w{2,4}(\\/.*)?$"
    return testRegex(regex, value.toLowerCase())
  }

  fun matcherVehicleNumber(value: String): Boolean {
    val regex = "^[京津晋冀蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云藏陕甘青宁新渝]?[A-Z][A-HJ-NP-Z0-9学挂港澳练]{5}$"
    return testRegex(regex, value.toLowerCase())
  }


  fun matcherIdentityCard(value: String): Boolean {
    //        String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|" +
    //                "(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|" +
    //                "\\d{3}[Xx])$)$";
    //        return testRegex(regex, value);
    val idCardTester = IDCardTester()
    return idCardTester.test(value)
  }

  private class IDCardTester {

    internal val WEIGHT = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

    internal val VALID = charArrayOf('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2')
    fun test(content: String): Boolean {
      if (TextUtils.isEmpty(content)) {
        return false
      }
      val length = content.length
      return if (15 == length) {
        try {
          isOldCNIDCard(content)
        } catch (e: NumberFormatException) {
          e.printStackTrace()
          false
        }

      } else if (18 == length) {
        isNewCNIDCard(content)
      } else {
        false
      }
    }

    fun isNewCNIDCard(numbers: String): Boolean {
      var numbers = numbers
      numbers = numbers.toUpperCase()
      var sum = 0
      for (i in WEIGHT.indices) {
        val cell = Character.getNumericValue(numbers[i])
        sum += WEIGHT[i] * cell
      }
      val index = sum % 11
      return VALID[index] == numbers[17]
    }

    fun isOldCNIDCard(numbers: String): Boolean {
      val yymmdd = numbers.substring(6, 11)
      val aPass = numbers == java.lang.Long.parseLong(numbers).toString()
      var yPass = true
      try {
        SimpleDateFormat("yyMMdd").parse(yymmdd)
      } catch (e: Exception) {
        e.printStackTrace()
        yPass = false
      }

      return aPass && yPass
    }
  }

  fun isNumeric(input: String): Boolean {
    if (TextUtils.isEmpty(input)) {
      return false
    }
    val chars = input.toCharArray()
    var sz = chars.size
    var hasExp = false
    var hasDecPoint = false
    var allowSigns = false
    var foundDigit = false
    val start = if (chars[0] == '-' || chars[0] == '+') 1 else 0
    if (sz > start + 1) {
      if (chars[start] == '0' && chars[start + 1] == 'x') {
        var i = start + 2
        if (i == sz) {
          return false
        }
        while (i < chars.size) {
          if ((chars[i] < '0' || chars[i] > '9')
            && (chars[i] < 'a' || chars[i] > 'f')
            && (chars[i] < 'A' || chars[i] > 'F')
          ) {
            return false
          }
          i++
        }
        return true
      }
    }
    sz--
    var i = start
    while (i < sz || i < sz + 1 && allowSigns && !foundDigit) {
      if (chars[i] >= '0' && chars[i] <= '9') {
        foundDigit = true
        allowSigns = false

      } else if (chars[i] == '.') {
        if (hasDecPoint || hasExp) {
          return false
        }
        hasDecPoint = true
      } else if (chars[i] == 'e' || chars[i] == 'E') {
        if (hasExp) {
          return false
        }
        if (!foundDigit) {
          return false
        }
        hasExp = true
        allowSigns = true
      } else if (chars[i] == '+' || chars[i] == '-') {
        if (!allowSigns) {
          return false
        }
        allowSigns = false
        foundDigit = false
      } else {
        return false
      }
      i++
    }
    if (i < chars.size) {
      if (chars[i] >= '0' && chars[i] <= '9') {
        return true
      }
      if (chars[i] == 'e' || chars[i] == 'E') {
        return false
      }
      if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
        return foundDigit
      }
      return if (chars[i] == 'l' || chars[i] == 'L') {
        foundDigit && !hasExp
      } else false
    }
    return !allowSigns && foundDigit
  }

  fun testRegex(regex: String, inputValue: String?): Boolean {
    inputValue?:return false
    return Pattern.compile(regex).matcher(inputValue).matches()
  }

  fun checkPostcode(postcode: String?): Boolean {
    postcode ?: return false
    val regex = "[0-9]\\d{5}"
    return Pattern.matches(regex, postcode)
  }

  /**
   * 判断字符串是否是数字
   */
  fun isNumber(value: String): Boolean {
    return isInteger(value) || isDouble(value)
  }

  /**
   * 判断字符串是否是整数
   */
  fun isInteger(value: String): Boolean {
    try {

      Integer.parseInt(value)
      return true
    } catch (e: NumberFormatException) {
      return false
    }

  }

  /**
   * 判断字符串是否是浮点数
   */
  fun isDouble(value: String): Boolean {
    try {

      java.lang.Double.parseDouble(value)
      return if (value.contains(".")) true else false
    } catch (e: NumberFormatException) {
      return false
    }

  }

  fun validateIsEmpty(content: String): Boolean {
    return TextUtils.isEmpty(content)
  }

  fun validatePhone(phone: String): Boolean {
    return !TextUtils.isEmpty(phone)
  }
}
