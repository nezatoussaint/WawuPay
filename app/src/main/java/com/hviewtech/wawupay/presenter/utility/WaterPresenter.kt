package com.hviewtech.wawupay.presenter.utility

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.utility.WaterCharge
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.utility.WaterContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UtilityModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

class WaterPresenter @Inject constructor(private val mUtilityModel: UtilityModel) :
  BasePresenter<WaterContract.View>(), WaterContract.Presenter {


  fun createWaterOrder(
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    volume: Int
  ) {

    val userId = HawkExt.info?.userId
    userId ?: return

    mUtilityModel.createWaterOrder(userId, NumberUtils.valueOf(amount), meterNumber, meterHolder, orderNo, volume)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(mView) {
        override fun onNext(data: Any?) {
          super.onNext(data)

          mView?.showCreateOrder(true)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showCreateOrder(false)
        }
      })
  }

  fun createWaterOffer(
    amount: BigDecimal,
    meterNumber: String
  ) {

    val userId = HawkExt.info?.userId
    userId ?: return

    mUtilityModel.createWaterOffer(userId, amount, meterNumber)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<WaterCharge?>(mView) {
        override fun onNext(data: WaterCharge?) {
          super.onNext(data)

          mView?.showOfferResult(data)
        }
      })
  }

}