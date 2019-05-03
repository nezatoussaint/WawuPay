package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class ElectricityOrders(
  var data: List<ElectricityOrder>? = listOf(),
  var page: Int? = 0 // 2
)

data class ElectricityOrder(
  var amount: BigDecimal = BigDecimal.ZERO, // 4800
  var dateCreated: Long = 0, // null
  var id: String = "", // 6
  var kwt: Int? = 0, // 16
  var tokenNumber: String = "", // 6
  var meterHolder: String? = "", // gatete Dieudonne
  var meterNumber: String? = "", // 444-444-4433-334
  var orderNo: String? = "", // EL389753434
  var status: Int = 0, // null
  var userId: Int? = 0 // 123
)