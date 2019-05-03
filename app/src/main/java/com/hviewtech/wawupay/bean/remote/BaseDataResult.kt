package com.hviewtech.wawupay.bean.remote

import com.google.gson.annotations.SerializedName

data class BaseDataResult<T>(
  //  var error: String? = null,
  //  var item: String? = null,
  //  var status: Int,
  //  var data: T


  @SerializedName("RETURN_DATA")
  var data: Data<T>? = null,
  @SerializedName("RETURN_MSG")
  var msg: String? = null,
  @SerializedName("RETURN_CODE")
  var code: Int = 0
)