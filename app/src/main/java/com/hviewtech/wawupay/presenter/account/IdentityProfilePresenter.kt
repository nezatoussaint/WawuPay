package com.hviewtech.wawupay.presenter.account

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.ResourceUtils
import com.hviewtech.wawupay.contract.account.IdentityProfileContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.DataTransformer
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.ImgModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class IdentityProfilePresenter @Inject constructor(private val mApiModel: ApiModel, private val mImgModel: ImgModel) :
  BasePresenter<IdentityProfileContract.View>(), IdentityProfileContract.Presenter {

  override fun uploadFrontPic(uri: String?) {
    val file = ResourceUtils.uri2File(uri)
    file ?: return
    mImgModel.putUploadImage(file)
      .bindToLifecycle(this)
      .compose<String>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<String?>(mView) {
        override fun onNext(data: String?) {
          mView?.showUploadFrontPicSuccess(data)
        }
      })
  }

  override fun uploadBackPic(uri: String?) {
    val file = ResourceUtils.uri2File(uri)
    file ?: return
    mImgModel.putUploadImage(file)
      .bindToLifecycle(this)
      .compose<String>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<String?>(mView) {
        override fun onNext(data: String?) {
          mView?.showUploadBackPicSuccess(data)
        }
      })
  }

  override fun uploadCertificationAndLogin(
    idType: Int, idNo: String?, realName: String?, idFrontPic: String?, idBackPic: String?
  ) {
    val token = HawkExt.token
    val userInfo = HawkExt.info

    token ?: return
    userInfo ?: return

    mApiModel.postUserCertificateUpload(userInfo.num, idType, idNo, realName, idFrontPic, idBackPic)
      .bindToLifecycle(this)
      .subscribeOn(Schedulers.io())
      .compose(DataTransformer())
      .flatMap<BaseResult<Login?>> { obj -> mApiModel.postUserLoginByToken(token) }
      .observeOn(AndroidSchedulers.mainThread())
      .compose(DataTransformer())
      .subscribe(object : SimpleSubscriber<Login?>(mView) {
        override fun onNext(data: Login?) {
          mView?.showLoginSuccess(data)
        }

      })
  }

  override fun loginByToken() {
    val token = HawkExt.token
    token ?: return
    mApiModel.postUserLoginByToken(token)
      .bindToLifecycle(this)
      .compose<Login>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Login>(mView) {
        override fun onNext(data: Login) {
          mView?.showLoginSuccess(data)
        }
      })
  }
}
