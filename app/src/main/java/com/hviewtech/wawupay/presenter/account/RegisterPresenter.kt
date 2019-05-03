package com.hviewtech.wawupay.presenter.account

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.bean.remote.account.VerCode
import com.hviewtech.wawupay.contract.account.RegisterContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.DataTransformer
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class RegisterPresenter @Inject constructor(private val mModel: ApiModel) : BasePresenter<RegisterContract.View>(),
  RegisterContract.Presenter {

  override fun getSmsCode(phone: String?) {
    mModel.postSendVerCode(phone)
      .bindToLifecycle(this)
      .compose<VerCode>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<VerCode>(mView) {
        override fun onNext(data: VerCode) {
          mView?.showVerCode(data.verCode)
        }
      })
  }

  override fun verficatePhone(phone: String?, code: String?) {
    mModel.postVerificationPhone(phone, code)
      .bindToLifecycle(this)
      .compose<Any>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any>(mView) {
        override fun onNext(data: Any) {
          mView?.showVericatePhoneSuccess()
        }
      })
  }

  override fun register(
    nickname: String?, firstName: String?, lastName: String?, email: String?, phone: String?, loginPassword: String?
  ) {
    mModel.postUserRegister(nickname, firstName, lastName, email, phone, loginPassword)
      .bindToLifecycle(this)
      .subscribeOn(Schedulers.io())
      .compose(DataTransformer())
      .flatMap({ obj -> mModel.postUserLogin(phone, loginPassword) })
      .observeOn(AndroidSchedulers.mainThread())
      .compose(DataTransformer())
      .subscribe(object : SimpleSubscriber<Login?>(mView) {
        override fun onNext(data: Login?) {
          mView?.showRegisterSuccess(data)
        }

      })
  }

}
