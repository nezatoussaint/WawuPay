package com.hviewtech.wawupay.bean.remote.wallet

import java.io.Serializable
import java.math.BigDecimal

/**
 * Created by su on 2018/4/11.
 */

data class BalanceInfo(
    /**
     * enabledBalance : 1100
     * accountId : 2
     * freezedBalance : 0
     * accountName : 张银
     * accountBalance : 1100
     * status : 1
     */

    var enabledBalance: BigDecimal? = null,
    var accountId: Int = 0,
    var freezedBalance: BigDecimal? = null,
    var accountName: String? = null,
    var accountBalance: BigDecimal? = null,
    var status: Int = 0
) : Serializable
