package com.hviewtech.wawupay.presenter.utility

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.utility.TopupOrders
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.utility.TopupOrdersContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UtilityModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class TopupOrdersPresenter @Inject constructor(private val mUtilityModel: UtilityModel) :
  BasePresenter<TopupOrdersContract.View>(), TopupOrdersContract.Presenter {


  fun getTopupOrders(page: Int, showloading: Boolean = false) {

    val userId = HawkExt.info?.userId
    userId ?: return

    val count = 20
    mUtilityModel.getTopupOrders(userId, page, count)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<TopupOrders?>(if (showloading) mView else null) {
        override fun onNext(data: TopupOrders?) {
          super.onNext(data)

          mView?.showOrders(true, data)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showOrders(false)
        }
      })
  }

}