package com.hviewtech.wawupay.bean.local

import java.io.Serializable

data class Traveller(
  var firstName: String? = null,
  var lastName: String? = null,
  var phoneNumber: String? = null

) : Serializable