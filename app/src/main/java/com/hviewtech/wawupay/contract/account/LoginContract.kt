package com.hviewtech.wawupay.contract.account

import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */

interface LoginContract {
    // 登录
    interface View : Contract.View {

        fun showLoginSuccess(result: Login)
    }

    interface Presenter : Contract.Presenter {
        fun login(username: String, password: String)
    }
}
