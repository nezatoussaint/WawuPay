package com.hviewtech.wawupay.common.utils

import com.hviewtech.wawupay.AUTHINFO
import com.hviewtech.wawupay.PLATFORM_FEE
import com.hviewtech.wawupay.TOKEN
import com.hviewtech.wawupay.USERINFO
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.bean.remote.account.UserInfo
import com.hviewtech.wawupay.bean.remote.transport.AuthInfo
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.orhanobut.hawk.Hawk

object HawkExt {

  var authInfo: AuthInfo?
    get() = Hawk.get(AUTHINFO)
    set(value) = Hawk.put(AUTHINFO, value)


  val authToken: String?
    get() = authInfo?.token

  val token: String?
    get() = Hawk.get(TOKEN)

  val info: UserInfo?
    get() = Hawk.get(USERINFO)

  val nickName: String?
    get() {
      val userInfo = Hawk.get<UserInfo>(USERINFO)
      return if (userInfo.nickname != null) userInfo.nickname else userInfo.num
    }

  val platformFee: PlatformFee?
    get() = Hawk.get(PLATFORM_FEE)

  fun clear() {
    Hawk.deleteAll()
  }

  fun saveInfo(result: Login?) {
    Hawk.put<UserInfo>(USERINFO, result?.bUserInfo)
    Hawk.put<String>(TOKEN, result?.token)
  }

  fun updateInfo(userInfo: UserInfo?) {
    Hawk.put(USERINFO, userInfo)
  }


  fun updatePlatformFee(result: PlatformFee?) {
    Hawk.put(PLATFORM_FEE, result)
  }
}
