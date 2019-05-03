package com.hviewtech.wawupay.contract.account

import com.hviewtech.wawupay.contract.Contract


/**
 * @author Eric
 * @version 1.0
 * @description
 */

interface ForgetPasswordContract {
  interface View : Contract.View {

    fun showVerCode(verCode: String?)

    fun showModifyPasswordSuccess()
  }

  interface Presenter : Contract.Presenter {

    fun getSmsCode(phone: String?)

    fun modifyPassword(phone: String?, verCode: String?, password: String?, passwordType: Int)
  }
}
