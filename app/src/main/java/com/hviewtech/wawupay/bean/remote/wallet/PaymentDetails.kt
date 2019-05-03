package com.hviewtech.wawupay.bean.remote.wallet

/**
 * @author su
 * @date 2018/3/25
 * @description
 */

data class PaymentDetails(

    /**
     * pageCount : 1
     * list : [{"relativeName":"店铺名称修改了","amount":100,"orderNo":"20180421212834148053264","relativeNum":"1","createTime":1524317327000,"billId":10,"remark":"用户二维码付款","category":2,"type":2}]
     * pageNow : 1
     */

    var pageCount: Int = 0,
    var pageNow: Int = 0,
    var list: List<PaymentDetail>? = null


)
