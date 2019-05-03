package com.hviewtech.wawupay.data.http.intercept

import android.os.Build
import android.text.TextUtils
import com.hviewtech.wawupay.BuildConfig
import com.hviewtech.wawupay.TOKEN
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class NetInterceptor() : Interceptor {

  var agent = "%s/%s (Android %s %s)"

  init {
    agent = String.format(agent, "MoneyPartner", BuildConfig.VERSION_NAME, Build.MANUFACTURER, Build.VERSION.RELEASE)
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val builder = request.newBuilder().removeHeader("User-Agent").header("User-Agent", agent)


    if (alreadyHasAuthorizationHeader(request)) {
      return chain.proceed(builder.build())
    }

    val token = Hawk.get<String>(TOKEN)

    if (token != null) {
      val newRequest = builder.header("Authorization", token).build()
      return chain.proceed(newRequest)
    }
    return chain.proceed(builder.build())
  }


  fun alreadyHasAuthorizationHeader(request: Request): Boolean {
    return !TextUtils.isEmpty(request.header("Authorization"))
  }


}