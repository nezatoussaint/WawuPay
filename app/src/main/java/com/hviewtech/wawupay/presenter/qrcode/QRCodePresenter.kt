package com.hviewtech.wawupay.presenter.qrcode

import com.google.gson.JsonObject
import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.PayInfo
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.contract.qrcode.QRCodeContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle

import javax.inject.Inject

/**
 * @author su
 * @date 2018/4/23
 * @description
 */

class QRCodePresenter @Inject constructor(private val mUserModel: UserModel) : BasePresenter<QRCodeContract.View>(),
  QRCodeContract.Presenter {


  fun requestNet(result: String) {
    mUserModel.requestNet(result)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<JsonObject?>(mView) {
        override fun onNext(data: JsonObject?) {
          var payInfo: PayInfo? = null
          val element = data?.get("qrType") ?: return
          val qrType = element.asInt
          val json = data.toString()
          if (qrType == 1) { // 付款页面
            payInfo = JsonUtils.parseObject(json, PayInfo::class.java)
          }

          mView?.showPaymentInfo(payInfo)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showPaymentInfo()
        }
      })
  }
}
