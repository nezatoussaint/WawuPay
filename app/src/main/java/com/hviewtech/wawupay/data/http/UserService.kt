package com.hviewtech.wawupay.data.http

import com.google.gson.JsonObject
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface UserService {

  companion object {
    val REL_URL = "http://167.99.89.30:8083"

    val DEV_URL = "http://167.99.89.30:8083"
  }

  //    @GET(ApiConst.getQRPlatformUser)
  //    Flowable<BaseResult<Contact?>> getQRPlatformUser(
  //            @Path("userNum") String userNum
  //    );


  @POST(ApiConst.postGetMerchantPosition)
  fun postGetMerchantPosition(
    @Body body: RequestBody
  ): Flowable<BaseResult<MerchantPositionList?>>

  @POST(ApiConst.postValidatePayPassword)
  fun postValidatePayPassword(
    @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postModifyPayPassword)
  fun postModifyPayPassword(
    @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>


  @GET
  fun requestNet(
    @Url path: String
  ): Flowable<BaseResult<JsonObject?>>


  @POST(ApiConst.postModifyUserInfo)
  fun postModifyUserInfo(
    @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postUserComplaintInfo)
  abstract fun postUserComplaintInfo(
    @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postGetUserBillInfoList)
  abstract fun postGetUserBillInfoList(
    @Body body: RequestBody
  ): Flowable<BaseResult<PaymentDetails?>>

}
