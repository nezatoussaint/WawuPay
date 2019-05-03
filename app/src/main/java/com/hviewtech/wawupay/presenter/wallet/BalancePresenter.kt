package com.hviewtech.wawupay.presenter.wallet

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.BalanceInfo
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.wallet.BalanceContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.PayModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/18
 * @description
 */

class BalancePresenter @Inject constructor(private val mPayModel: PayModel) : BasePresenter<BalanceContract.View>(),
  BalanceContract.Presenter {

  fun initBalanceInfo() {
    val accountId = HawkExt.info?.accountId
    accountId ?: return
    mPayModel.postGetAccountInfo(accountId)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<BalanceInfo?>() {
        override fun onNext(data: BalanceInfo?) {
          mView?.showBalanceInfo(data)
        }
      })

  }
}
