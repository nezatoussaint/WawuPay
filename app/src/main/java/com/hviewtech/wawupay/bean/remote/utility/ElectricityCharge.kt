package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class ElectricityCharge(
  var accept: Accept? = Accept()
) {
  data class Accept(
    var amount: BigDecimal = BigDecimal.ZERO, // 4800
    var dateCreated: Any? = Any(), // null
    var id: Any? = Any(), // null
    var kwt: Int = 0, // 16
    var meterHolder: String = "", // gatete dieudonne
    var meterNumber: String = "", // 123456789
    var orderNo: String = "", // ELZ4311118
    var paymentStatus: Any? = Any(), // null
    var userId: Int = 0 // 53
  )
}