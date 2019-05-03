package com.hviewtech.wawupay.bean.remote.account

import com.hviewtech.wawupay.common.annotations.Keep

@Keep
data class Login(
    /**
     * bUserInfo : {"accountId":33,"createTime":1521302650000,"dateBirth":null,"firstName":"名","idBackPic":"/xxx/xxx.jpg","idFrontPic":"/xxx/xxx.jpg","idNo":"5101840000000000","idType":1,"lastLoginTime":null,"lastName":"姓9","lastUpdateTime":1521302650000,"loginPasswordId":8,"nickname":"姓9","num":"90000009","payPasswordId":null,"phone":"13540604032","profilePic":"","realName":"张银","registerType":null,"sex":null,"status":1,"userId":9}
     * token : 04DFEB529E35BB4F1FF344ECAEF617CBC6E411B7CF94DA2032B8A3396BD6E8C0
     */

    var bUserInfo: UserInfo? = null,
    var token: String? = null


)
