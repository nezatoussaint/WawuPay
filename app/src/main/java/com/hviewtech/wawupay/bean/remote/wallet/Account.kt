package com.hviewtech.wawupay.bean.remote.wallet

import java.math.BigDecimal

/**
 * Created by su on 2018/4/11.
 */

data class Account(
    /**
     * amount : 90
     * optNo : 20180411210815493
     */

    var amount: BigDecimal? = null,
    var optNo: String? = null
)