package com.hviewtech.wawupay.presenter.wallet


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.Account
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.RechargeContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.PayModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by su on 2018/4/12.
 */

class RechargePresenter @Inject
constructor(private val mApiModel: ApiModel, private val mPayModel: PayModel) : BasePresenter<RechargeContract.View>(),
  RechargeContract.Presenter {

  fun rechargeByBankcard(bankCardId: String, amount: BigDecimal) {
    val info = HawkExt.info
    info ?: return
    val accountId = info.accountId
    val userType = 1
    val num = info.num

    mPayModel.postAccountRechargeForBankCard(accountId, userType, num, bankCardId, amount)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Account?>(mView) {
        override fun onNext(data: Account?) {
          mView?.rechargeSuccess()
        }
      })
  }

  fun getBankCardList() {
    val info = HawkExt.info
    if (info == null) {
      mView?.showMyBankList()
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

      })
  }
}
