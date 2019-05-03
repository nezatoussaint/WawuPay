package com.hviewtech.wawupay.presenter.account


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.account.VerCode
import com.hviewtech.wawupay.contract.account.ForgetPasswordContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle

import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class ForgetPasswordPresenter @Inject constructor(private val mApiModel: ApiModel) :
  BasePresenter<ForgetPasswordContract.View>(),
  ForgetPasswordContract.Presenter {

  override fun getSmsCode(phone: String?) {
    mApiModel.postSendVerCode(phone)
      .bindToLifecycle(this)
      .compose<VerCode>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<VerCode>(mView) {
        override fun onNext(data: VerCode) {
          if (mView != null) {
            mView!!.showVerCode(data.verCode)
          }
        }
      })
  }

  override fun modifyPassword(phone: String?, verCode: String?, password: String?, passwordType: Int) {
    val customerType = 1
    mView?.showloading()
    mApiModel.postModifyPassword(phone, verCode, password, passwordType, customerType)
      .bindToLifecycle(this)
      .compose<Any>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any>() {
        override fun onNext(data: Any) {
          mView?.dismissloading {
            mView?.showModifyPasswordSuccess()
          }
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.dismissloading(true)
        }

      })
  }

}
