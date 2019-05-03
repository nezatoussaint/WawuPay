package com.hviewtech.wawupay.bean.remote.transport

import java.io.Serializable

data class SiteInfo(
  var id: String? = "",
  var destination_name: String? = "",
  var price: Int = 0,
  var route_name: String? = "",
  var trip_time: String? = "",
  var route_duration: String? = "",
  var company_name: String? = "",
  var start: String? = "",
  var end: String? = "",
  var schedule_time: String? = ""
) : Serializable