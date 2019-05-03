package com.hviewtech.wawupay.bean.remote.transport

data class Order(
  var id: Int? = 0,
  var ticketId: String? = "",
  var userId: Int? = 0,
  var phoneNumber: String? = "",
  var customerName: String? = "",
  var destination: String? = "",
  var date: String? = "",
  var time: String? = "",
  var agent: String? = "",
  var tt: String? = "",
  var posId: String? = "",
  var plate: String? = "",
  var channel: String? = "",
  var flagLuggage: String? = "",
  var kg: Int? = 0,
  var status: Int? = 0,
  var price: Int? = 0,
  var luggagePrice: Int? = 0,
  var totalAmount: Int = 0,
  var paymentStatus: Int = 0,
  var printStatus: Int? = 0,
  var createTime: String? = "",
  var paymentTime: String? = ""
)