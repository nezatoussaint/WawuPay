package com.hviewtech.wawupay.presenter.home

import com.google.gson.JsonObject
import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.ResourceUtils
import com.hviewtech.wawupay.contract.home.MainContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.ImgModel
import com.hviewtech.wawupay.model.PayModel
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */

class MainPresenter @Inject constructor(
  private val mApiModel: ApiModel,
  private val mImgModel: ImgModel,
  private val mPayModel: PayModel,
  private val mUserModel: UserModel
) :
  BasePresenter<MainContract.View>(), MainContract.Presenter {

  override fun getPlatformFee() {
    val type = 1
    mPayModel.postGetPlatformFee(type)
      .bindToLifecycle(this)
      .compose<PlatformFee?>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<PlatformFee?>() {

        override fun onNext(data: PlatformFee?) {
          HawkExt.updatePlatformFee(data)
        }
      })
  }

  override fun checkPayPasswordExist() {
    val userType = 1
    val num = HawkExt.info?.num

    mApiModel.postPayPasswordIsExist(userType, num)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(mView) {
        override fun onNext(data: Any?) {
          mView?.checkPayPasswordResult(true)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.checkPayPasswordResult(false)
        }

      })
  }

  override fun uploadAvatar(path: String?) {
    val file = ResourceUtils.uri2File(path)
    file?:return
    mImgModel.putUploadImage(file)
      .bindToLifecycle(this)
      .compose<String>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<String?>(mView) {
        override fun onNext(data: String?) {
            mView?.showAvatar(data)
        }
      })
  }

  override fun modifyUserProfilePic(url: String?) {
    val num = HawkExt.info?.num
    val obj = JsonObject()
    obj.addProperty("num", num)
    obj.addProperty("profilePic", url)
    val json = obj.toString()
    mUserModel.postModifyUserInfo(json)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          val info = HawkExt.info
          info?.profilePic = url
          HawkExt.updateInfo(info)
        }
      })
  }

}
