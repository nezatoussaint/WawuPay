package com.hviewtech.wawupay.contract

import com.hviewtech.wawupay.common.ext.DEFAULT


/**
 * @author su
 * @date 2018/11/23
 * @description
 */
interface Contract {

  interface View {
    fun showloading()

    fun dismissloading(error: Boolean = false, handler: () -> Unit = Unit.DEFAULT)

    fun showError(msg: String?)
  }

  interface Presenter {
    fun attachView(view: Contract.View)

    fun detachView()

    fun destroy()
  }
}