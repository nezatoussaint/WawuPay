package com.hviewtech.wawupay.bean.remote.wallet

import java.math.BigDecimal

/**
 * Created by su on 2018/4/11.
 */

data class PlatformFee(
    /**
     * userWithdrawMaxAmount : 21654100
     * userWithdrawFee : 10
     * giftMaxAmount : 1222000
     * shopPayMaxAmount : 24500
     * transferMaxAmount : 23454100
     * apiPayMaxAmount : 100321
     * userRechargeMaxAmount : 10000
     */

    var userWithdrawMaxAmount: BigDecimal? = null,
    var userWithdrawFee: Float = 0f,
    var giftMaxAmount: BigDecimal? = null,
    var shopPayMaxAmount: BigDecimal? = null,
    var transferMaxAmount: BigDecimal? = null,
    var apiPayMaxAmount: BigDecimal? = null,
    var userRechargeMaxAmount: BigDecimal? = null
)
