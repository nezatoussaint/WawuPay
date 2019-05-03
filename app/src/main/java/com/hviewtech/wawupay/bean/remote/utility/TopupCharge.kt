package com.hviewtech.wawupay.bean.remote.utility

import java.math.BigDecimal

data class TopupCharge(
  var data: Data? = Data(),
  var companyName: String? = "" // 2
) {
  data class Data(
    var amount: BigDecimal = BigDecimal.ZERO, // 100
    var date: String? = "", // Sat Dec 22 03:52:36 UTC 2018
    var meterNumber: String? = "", // 07888
    var orderNumber: String? = "", // Top5311811352
    var username: String? = "", // gaet
    var value: Any? = Any() // null
  )
}