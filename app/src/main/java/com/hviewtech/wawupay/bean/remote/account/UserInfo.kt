package com.hviewtech.wawupay.bean.remote.account

import com.hviewtech.wawupay.common.annotations.Keep

@Keep
data class UserInfo(
    /**
     * accountId : 33
     * createTime : 1521302650000
     * dateBirth : null
     * firstName : 名
     * idBackPic : /xxx/xxx.jpg
     * idFrontPic : /xxx/xxx.jpg
     * idNo : 5101840000000000
     * idType : 1
     * lastLoginTime : null
     * lastName : 姓9
     * lastUpdateTime : 1521302650000
     * loginPasswordId : 8
     * nickname : 姓9
     * num : 90000009
     * payPasswordId : null
     * phone : 13540604032
     * profilePic :
     * realName : 张银
     * registerType : null
     * sex : null
     * status : 1
     * userId : 9
     */

    var accountId: String? = null,
    var createTime: Long = 0,
    var dateBirth: String? = null,
    var firstName: String? = null,
    var idBackPic: String? = null,
    var idFrontPic: String? = null,
    var idNo: String? = null,
    var idType: Int = 0,
    var lastLoginTime: String? = null,
    var lastName: String? = null,
    var lastUpdateTime: Long = 0,
    var loginPasswordId: Int = 0,

    var nickname: String? = "",
    var num: String? = null,
    var payPasswordId: String? = null,
    var phone: String? = null,
    var profilePic: String? = null,
    var realName: String? = null,
    var registerType: Int = 0,
    var sex: Int = 0,
    var status: Int = 0,
    var userId: String? = null
)