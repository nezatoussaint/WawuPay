package com.hviewtech.wawupay.bean.remote.wallet

import java.io.Serializable
import java.math.BigDecimal

/**
 * Created by su on 2018/4/11.
 */

data class PayInfo(
    /**
     * realName : 张银
     * amount : 0
     * phone : 13540604032
     * paySign : AA15839173FAA2414FCDE93C66521A85E3D64CB26875AD5F6481809B481C9DFA6FD5AEBB46230466B4F69F5752BF673857C284F1739B25226B481C1B7CCCBBA0D29617B40C16DCA3C61FEE4488BAF8270BCAA299CD8133F7CB777B1C5A08E230
     * num : 80000002
     * profilePic :
     * nickname : null
     * qrType : 1
     * userType : 2
     */

    var realName: String? = null,
    var amount: BigDecimal? = null,
    var phone: String? = null,
    var paySign: String? = null,
    var num: String? = null,
    var profilePic: String? = null,
    var nickname: String? = null,
    var qrType: Int = 0,
    var userType: Int = 0
) : Serializable
