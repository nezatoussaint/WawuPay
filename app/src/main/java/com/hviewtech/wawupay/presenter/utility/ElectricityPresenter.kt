package com.hviewtech.wawupay.presenter.utility

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.utility.ElectricityCharge
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.contract.utility.ElectricityContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UtilityModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

class ElectricityPresenter @Inject constructor(private val mUtilityModel: UtilityModel) :
  BasePresenter<ElectricityContract.View>(), ElectricityContract.Presenter {


  fun createElectricityOrder(
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    kwh: Int
  ) {

    val userId = HawkExt.info?.userId
    userId ?: return

    mUtilityModel.createElectricityOrder(userId, amount, meterNumber, meterHolder, orderNo, kwh)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(mView) {
        override fun onNext(data: Any?) {
          super.onNext(data)

          mView?.showCreateOrder(true)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showCreateOrder()
        }
      })
  }

  fun createElectricityOffer(
    amount: BigDecimal,
    meterNumber: String
  ) {

    val userId = HawkExt.info?.userId
    userId ?: return

    mUtilityModel.createElectricityOffer(userId, amount, meterNumber)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<ElectricityCharge?>(mView) {
        override fun onNext(data: ElectricityCharge?) {
          super.onNext(data)

          mView?.showOfferResult(data)
        }
      })
  }

}