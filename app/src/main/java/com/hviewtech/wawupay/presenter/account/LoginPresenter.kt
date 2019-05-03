package com.hviewtech.wawupay.presenter.account


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.account.LoginContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle

import javax.inject.Inject

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */

class LoginPresenter @Inject constructor(internal var mModel: ApiModel) : BasePresenter<LoginContract.View>(),
  LoginContract.Presenter {

  override fun login(username: String, password: String) {
    mModel.postUserLogin(username, password)
      .bindToLifecycle(this)
      .compose<Login>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Login>(mView) {
        override fun onNext(data: Login) {
          HawkExt.saveInfo(data)
          mView?.showLoginSuccess(data)
        }
      })
  }

}
