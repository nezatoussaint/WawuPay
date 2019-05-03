package com.hviewtech.wawupay.bean.remote.wallet

import java.io.Serializable

data class BankCard(
    /**
     * realName : BPR
     * bankName : Bank Populaire du Rwanda
     * cardNo : 2415645456154
     * bankCardId : 1
     * status : 1
     */

    var realName: String? = null,
    var bankName: String? = null,
    var cardNo: String? = null,
    var bankCardId: Int = 0,
    var status: Int = 0
) : Serializable