package com.hviewtech.wawupay.presenter.wallet


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.wallet.Account
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import com.hviewtech.wawupay.bean.remote.wallet.PlatformFee
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.wallet.WithdrawDepositContract
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

class WithdrawDepositPresenter @Inject constructor(private val mApiModel: ApiModel, private val mPayModel: PayModel) :
  BasePresenter<WithdrawDepositContract.View>(), WithdrawDepositContract.Presenter {

  fun getPlatformFee() {
    val type = 1
    mPayModel.postGetPlatformFee(type)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<PlatformFee?>() {
        override fun onNext(data: PlatformFee?) {
          HawkExt.updatePlatformFee(data)
          mView?.updatePlatformFee(data)
        }
      })
  }


  fun withdrawToBankCard(bankId: String, amount: BigDecimal) {
    val info = HawkExt.info
    info ?: return
    val accountId = info.accountId
    val num = info.num
    val userType = 1
    mPayModel.postAccountWithdrawForBankCard(accountId, userType, num, bankId, amount)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Account?>(mView) {
        override fun onNext(data: Account?) {
          if (mView != null) {
            mView!!.showWithdrawResult(data)
          }
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
