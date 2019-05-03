package com.hviewtech.wawupay.data.http

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.bean.remote.account.Register
import com.hviewtech.wawupay.bean.remote.account.VerCode
import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.bean.remote.map.CategoryList
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
interface ApiService {

  companion object {
    const val DEV_URL = "http://167.99.89.30:8082"
    const val REL_URL = "http://167.99.89.30:8082"
  }

  @POST(ApiConst.postSendVerCode)
  fun postSendVerCode(
          @Path("phone") phone: String?
  ): Flowable<BaseResult<VerCode?>>


  @POST(ApiConst.postVerificationPhone)
  fun postVerificationPhone(
          @Path("phone") phone: String?,
          @Path("code") code: String?
  ): Flowable<BaseResult<Any?>>


  @POST(ApiConst.postGetProvinceCitys)
  fun postGetProviceCitys(): Flowable<BaseResult<ProvinceCitys?>>


  @POST(ApiConst.postModifyPassword)
  fun postModifyPassword(
          @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postUserLogin)
  fun postUserLogin(
          @Body body: RequestBody
  ): Flowable<BaseResult<Login?>>

  @POST(ApiConst.postUserLoginByToken)
  fun postUserLoginByToken(
          @Path("token") token: String?
  ): Flowable<BaseResult<Login?>>

  @POST(ApiConst.postUserIsRealRegister)
  fun postUserIsRealRegister(
          @Path("num") num: String?
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postUserRegister)
  fun postUserRegister(
          @Body body: RequestBody
  ): Flowable<BaseResult<Register?>>

  @POST(ApiConst.postUserCertificateUpload)
  fun postUserCertificateUpload(
          @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postAddBankCard)
  fun postAddBankCard(
          @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postDelBankCard)
  fun postDelBankCard(
          @Path("bankCardId") bankCardId: Int
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postBankCardList)
  fun postBankCardList(
          @Body body: RequestBody
  ): Flowable<BaseResult<BankCardList?>>

  @POST(ApiConst.postPayPasswordIsExist)
  fun postPayPasswordIsExist(
          @Body body: RequestBody
  ): Flowable<BaseResult<Any?>>

  @POST(ApiConst.postGetShopCategories)
  fun postGetShopCategories(): Flowable<BaseResult<CategoryList?>>
}