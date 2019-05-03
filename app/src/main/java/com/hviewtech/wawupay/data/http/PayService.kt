package com.hviewtech.wawupay.data.http

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.transport.MerPrepayId
import com.hviewtech.wawupay.bean.remote.wallet.*
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PayService {

  companion object {
    val DEV_URL = "http://167.99.89.30:8085"
    val REL_URL = "http://167.99.89.30:8085"
  }

  @POST(ApiConst.postGetAccountInfo)
  fun postGetAccountInfo(
    @Path("accountId") accountId: String?
  ): Flowable<BaseResult<BalanceInfo?>>


  @POST(ApiConst.postGetPlatformFee)
  fun postGetPlatformFee(
    @Path("type") type: Int
  ): Flowable<BaseResult<PlatformFee?>>

  @POST(ApiConst.postAccountWithdrawForBankCard)
  fun postAccountWithdrawForBankCard(
    @Body body: RequestBody
  ): Flowable<BaseResult<Account?>>

  @POST(ApiConst.postAccountRechargeForBankCard)
  fun postAccountRechargeForBankCard(
    @Body body: RequestBody
  ): Flowable<BaseResult<Account?>>

  @POST(ApiConst.postGetUserPrepayId)
  fun postGetUserPrepayId(
    @Body body: RequestBody
  ): Flowable<BaseResult<PrepayId?>>

  @POST(ApiConst.postQrGainPay)
  fun postQrGainPay(
    @Path("paySign") paySign: String?
  ): Flowable<BaseResult<PayInfo?>>

  @POST(ApiConst.postQrPayment)
  fun postQrPayment(
    @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postGetAPIMerPrepayId)
  fun postGetAPIMerPrepayId(
    @Body body: RequestBody
  ): Flowable<BaseResult<MerPrepayId?>>
}
