package com.hviewtech.wawupay.model.impl

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.transport.MerPrepayId
import com.hviewtech.wawupay.bean.remote.wallet.*
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.PayService
import com.hviewtech.wawupay.model.PayModel
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by su on 2018/4/11.
 */

class PayModelImpl @Inject constructor(private val mPayService: PayService) : PayModel {

  override fun postGetAccountInfo(accountId: String?): Flowable<BaseResult<BalanceInfo?>> {
    return mPayService.postGetAccountInfo(accountId)
  }

  override fun postGetPlatformFee(type: Int): Flowable<BaseResult<PlatformFee?>> {
    return mPayService.postGetPlatformFee(type)
  }

  override fun postAccountWithdrawForBankCard(
    accountId: String?,
    userType: Int,
    num: String?,
    bankCardId: String?,
    amount: BigDecimal
  ): Flowable<BaseResult<Account?>> {
    val obj = HashMap<String, Any?>()
    obj["accountId"] = accountId
    obj["userType"] = userType
    obj["num"] = num
    obj["bankCardId"] = bankCardId
    obj["amount"] = amount
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mPayService.postAccountWithdrawForBankCard(body)
  }

  override fun postAccountRechargeForBankCard(
    accountId: String?,
    userType: Int,
    num: String?,
    bankCardId: String?,
    amount: BigDecimal
  ): Flowable<BaseResult<Account?>> {
    val obj = HashMap<String, Any?>()
    obj["accountId"] = accountId
    obj["userType"] = userType
    obj["num"] = num
    obj["bankCardId"] = bankCardId
    obj["amount"] = amount
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mPayService.postAccountRechargeForBankCard(body)
  }

  override fun postGetUserPrepayId(accountId: String?, amount: BigDecimal): Flowable<BaseResult<PrepayId?>> {
    val obj = HashMap<String, Any?>()
    obj["accountId"] = accountId
    obj["amount"] = amount
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mPayService.postGetUserPrepayId(body)
  }

  override fun postQrGainPay(paySign: String?): Flowable<BaseResult<PayInfo?>> {
    return mPayService.postQrGainPay(paySign)
  }

  override fun postQrPayment(
    paySign: String,
    amount: BigDecimal,
    accountId: String,
    payPassword: String
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["paySign"] = paySign
    obj["amount"] = amount
    obj["accountId"] = accountId
    obj["payPassword"] = payPassword
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mPayService.postQrPayment(body)
  }

  override fun postGetAPIMerPrepayId(
    itemType: Int,
    amount: BigDecimal,
    itemId: String
  ): Flowable<BaseResult<MerPrepayId?>> {
    val obj = HashMap<String, Any?>()
    // itemType与appId目前一致
    obj["itemType"] = itemType
    obj["appId"] = itemType
    obj["amount"] = amount
    obj["itemId"] = itemId
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mPayService.postGetAPIMerPrepayId(body)
  }
}
