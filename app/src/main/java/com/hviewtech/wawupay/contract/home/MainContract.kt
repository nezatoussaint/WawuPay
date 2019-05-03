package com.hviewtech.wawupay.contract.home

import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */

interface MainContract {

  interface View : Contract.View {

    fun checkPayPasswordResult(isExist: Boolean)


    fun showAvatar(url: String?)

  }

  interface Presenter : Contract.Presenter {
    fun getPlatformFee()

    fun checkPayPasswordExist()

    fun uploadAvatar(path: String?)

    fun modifyUserProfilePic(url: String?)

  }

}
