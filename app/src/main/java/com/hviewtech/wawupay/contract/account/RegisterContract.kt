package com.hviewtech.wawupay.contract.account


import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @version 1.0
 * @description
 */

interface RegisterContract {
  interface View : Contract.View {


    fun showVerCode(verCode: String?)

    fun showVericatePhoneSuccess()

    fun showRegisterSuccess(result: Login?)
  }

  interface Presenter : Contract.Presenter {

    fun getSmsCode(phone: String?)

    fun verficatePhone(phone: String?, code: String?)

    fun register(
      nickname: String?, firstName: String?, lastName: String?, email: String?, phone: String?, loginPassword: String?
    )
  }
}
