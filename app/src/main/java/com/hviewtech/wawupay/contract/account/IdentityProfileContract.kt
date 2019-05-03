package com.hviewtech.wawupay.contract.account

import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @version 1.0
 * @description
 */

interface IdentityProfileContract {
  interface View : Contract.View {

    fun showUploadFrontPicSuccess(url: String?)

    fun showUploadBackPicSuccess(url: String?)

    fun showLoginSuccess(result: Login?)
  }

  interface Presenter : Contract.Presenter {

    fun uploadFrontPic(uri: String?)

    fun uploadBackPic(uri: String?)

    fun uploadCertificationAndLogin(
      idType: Int,
      idNo: String?,
      realName: String?,
      idFrontPic: String?,
      idBackPic: String?
    )

    fun loginByToken()
  }
}
