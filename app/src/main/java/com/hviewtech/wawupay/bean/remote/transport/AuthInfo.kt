package com.hviewtech.wawupay.bean.remote.transport

data class AuthInfo(
  var userId: Int? = 0,
  var userName: String? = "",
  var password: String? = "",
  var balance: Int? = 0,
  var token: String? = ""
)