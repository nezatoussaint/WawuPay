package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class WaterCharge(
  var data: Data? = Data()
) {
  data class Data(
    var amount: BigDecimal = BigDecimal.ZERO, // 5000
    var date: Any? = Any(), // null
    var id: Any? = Any(), // null
    var meterHolder: String = "", // gatete dieudonne
    var meterNumber: String = "", // 123456789
    var orderNo: String = "", // Top531181162
    var paymentStatus: Any? = Any(), // null
    var userId: Int = 0, // 53
    var volume: Int = 0 // 250
  )
}