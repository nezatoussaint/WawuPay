package com.hviewtech.wawupay.presenter.wallet.frag


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.wallet.frag.BankCardContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/24
 * @description
 */

class BankCardPresenter @Inject
constructor(private val mApiModel: ApiModel) : BasePresenter<BankCardContract.View>(), BankCardContract.Presenter {

  fun getBankCardList() {
    val info = HawkExt.info
    if (info == null) {
      mView?.showMyBankList(false, null)
      return
    }
    val userType = 1
    mApiModel.postBankCardList(userType, info.num)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<BankCardList?>() {
        override fun onNext(data: BankCardList?) {
          mView?.showMyBankList(true, data?.bankCardList)
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          mView?.showMyBankList(false, null)
        }
      })
  }

}
