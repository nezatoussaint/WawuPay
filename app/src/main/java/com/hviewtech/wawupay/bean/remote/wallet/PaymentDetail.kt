package com.hviewtech.wawupay.bean.remote.wallet

import java.io.Serializable
import java.math.BigDecimal

data class PaymentDetail(
    /**
     * relativeName : 店铺名称修改了
     * amount : 100
     * orderNo : 20180421212834148053264
     * relativeNum : 1
     * createTime : 1524317327000
     * billId : 10
     * remark : 用户二维码付款
     * category : 2
     * type : 2
     */

    var relativeName: String? = "",
    var amount: BigDecimal? = null,
    var orderNo: String? = null,
    var relativeNum: String? = "",
    var createTime: Long = 0,
    var billId: Int = 0,
    var remark: String? = null,
    var category: Int = 0,
    // 1收入，2支出
    var type: Int = 0
) : Serializable