package com.hviewtech.wawupay.common

import android.widget.Toast
import com.hviewtech.wawupay.BuildConfig
import com.hviewtech.wawupay.data.http.ApiConst
import com.hviewtech.wawupay.data.http.ImgService

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
object Constants {

  object URL {
    val HOST = "bingoz.top"
    val SERVER = "https://$HOST:8080"

    val USER_AGREEMENT = "http://hviewtech.com/policy/useragreement.html"
    val PRIVACY = "http://hviewtech.com/policy/privacypolicy.html"
    val ABOUT_US = "http://hviewtech.com/policy/AboutUs.html"
    val HELP = ""
  }

  object App {
    var TOAST: Toast? = null
  }

  object Prefix {
    val SEPARATOR = "/"
    val RESOURCE = "android.resource://" + BuildConfig.APPLICATION_ID
    val DRAWABLE = "$RESOURCE/drawable/"
    val RAW = "$RESOURCE/raw/"
    val IMG = ImgService.REL_URL + ApiConst.getShowImage
  }

  object Secure {
    val KEY = byteArrayOf(115, 83, 86, 74, 103, 55, 67, 57, 98, 120, 102, 114, 50, 119, 118, 66)
  }

  object RequestType {
    val JSON = "application/json"
  }

  object Status {
    val OK = 1
    val ERROR = 2
    val TOKEN_INVALID = 3
  }


  object ItemType {
    const val BUS = 1
    const val TOPUP = 2
    const val WATER = 3
    const val ELECTRICITY = 4
  }
}
