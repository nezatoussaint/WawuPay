package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class TopupOrders(
  var data: List<TopupOrder>? = arrayListOf(),
  var page: Int = 0
)

data class TopupOrder(
  var amount: BigDecimal = BigDecimal.ZERO,
  var companyName: String? = "",
  var date: String = "",
  var id: String = "",
  var orderNo: String? = "",
  var phoneNumber: String? = "",
  var userId: Int = 0,
  var status: Int = 0
)