package com.hviewtech.wawupay.bean.remote.transport

data class Site(
  var siteId: Int = 0,
  var siteName: String? = "",
  var status: Int = 0,
  var locationId: Int = 0
)