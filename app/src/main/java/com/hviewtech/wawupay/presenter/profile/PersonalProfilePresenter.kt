package com.hviewtech.wawupay.presenter.profile

import com.google.gson.JsonObject
import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.profile.PersonalProfileContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class PersonalProfilePresenter @Inject
constructor(private val mApiModel: ApiModel, private val mUserModel: UserModel) :
  BasePresenter<PersonalProfileContract.View>(), PersonalProfileContract.Presenter {

  fun initProvinceCities(showProgress: Boolean) {
    mApiModel.postGetProviceCitys()
      .bindToLifecycle(this)
      .compose<ProvinceCitys>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<ProvinceCitys?>(if (showProgress) mView else null) {
        override fun onNext(data: ProvinceCitys?) {
          mView?.updateProvinceCities(showProgress, data)
        }
      })
  }

  fun updateSex(sex: Int) {
    val num = HawkExt.info?.num

    num ?: return

    val obj = JsonObject()
    obj.addProperty("num", num)
    obj.addProperty("sex", sex)
    val json = obj.toString()
    mUserModel.postModifyUserInfo(json)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          val info = HawkExt.info
          info?.sex = sex
          HawkExt.updateInfo(info)
        }
      })
  }

  fun updateNickName(nickname: String?) {
    val num = HawkExt.info?.num
    num ?: return
    val obj = JsonObject()
    obj.addProperty("num", num)
    obj.addProperty("nickname", nickname)
    val json = obj.toString()
    mUserModel.postModifyUserInfo(json)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          val info = HawkExt.info
          info?.nickname = nickname
          HawkExt.updateInfo(info)
        }
      })
  }
}
