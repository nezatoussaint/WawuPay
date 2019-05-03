package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class WaterOrders(
  var data: List<WaterOrder>? = listOf(),
  var page: Int? = 0 // 2
)

data class WaterOrder(
  var amount: BigDecimal = BigDecimal.ZERO, // 5000
  var date: Long = 0, // 1545231434000
  var id: Int? = 0, // 4
  var meterHolder: String? = "", // gatete dieudonne
  var meterNumber: String? = "", // 123456789
  var orderNo: String? = "", // WAT118111355
  var status: Int = 0, // null
  var userId: Int? = 0, // 123
  var volume: Int? = 0, // 250
  var isChecked: Boolean = false
)