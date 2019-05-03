package com.hviewtech.wawupay.model

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.transport.MerPrepayId
import com.hviewtech.wawupay.bean.remote.wallet.*
import io.reactivex.Flowable

import java.math.BigDecimal

/**
 * Created by su on 2018/4/11.
 */

interface PayModel {
  fun postGetAccountInfo(
    accountId: String?
  ): Flowable<BaseResult<BalanceInfo?>>


  fun postGetPlatformFee(
    type: Int
  ): Flowable<BaseResult<PlatformFee?>>

  fun postAccountWithdrawForBankCard(
    accountId: String?,
    userType: Int,
    num: String?,
    bankCardId: String?,
    amount: BigDecimal
  ): Flowable<BaseResult<Account?>>

  fun postAccountRechargeForBankCard(
    accountId: String?,
    userType: Int,
    num: String?,
    bankCardId: String?,
    amount: BigDecimal
  ): Flowable<BaseResult<Account?>>


  fun postGetUserPrepayId(
    accountId: String?,
    amount: BigDecimal
  ): Flowable<BaseResult<PrepayId?>>

  fun postQrGainPay(
    paySign: String?
  ): Flowable<BaseResult<PayInfo?>>

  fun postQrPayment(
    paySign: String,
    amount: BigDecimal,
    accountId: String,
    payPassword: String
  ): Flowable<BaseResult<Any?>>


  fun postGetAPIMerPrepayId(
    itemType: Int,
    amount: BigDecimal,
    itemId: String
  ): Flowable<BaseResult<MerPrepayId?>>
}
