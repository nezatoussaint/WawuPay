package com.hviewtech.wawupay.presenter.wallet.frag


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.contract.wallet.frag.BankManageContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * Created by su on 2018/4/12.
 */

class BankManagePresenter @Inject
constructor(private val mApiModel: ApiModel) : BasePresenter<BankManageContract.View>(), BankManageContract.Presenter {


  fun delBankCard(bankCardId: Int) {
    mApiModel.postDelBankCard(bankCardId)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          mView?.showDelBankcardSuccess()
        }
      })
  }
}
