package com.hviewtech.wawupay.presenter.utility

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.utility.TopupCharge
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.utility.TopupContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UtilityModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

class TopupPresenter @Inject constructor(private val mUtilityModel: UtilityModel) :
  BasePresenter<TopupContract.View>(), TopupContract.Presenter {


  fun createTopupOrder(phoneNumber: String, companyName: String, amount: BigDecimal) {

    val userId = HawkExt.info?.userId
    userId ?: return

    mUtilityModel.createTopupOrder(userId, phoneNumber, companyName, amount)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<TopupCharge?>(mView) {
        override fun onNext(data: TopupCharge?) {
          super.onNext(data)

          mView?.showChargeInfo(data)
        }
      })
  }

}