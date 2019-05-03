package com.hviewtech.wawupay.bean.remote

import com.google.gson.annotations.SerializedName

/**
 * @author su
 * @date 2018/3/18
 * @description
 */

data class BaseResult<T>(

    @SerializedName("RETURN_DATA")
    var data: T? = null,
    @SerializedName("RETURN_MSG")
    var msg: String? = null,
    @SerializedName("RETURN_CODE")
    var code: Int = 0

)
