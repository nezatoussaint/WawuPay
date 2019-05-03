package com.hviewtech.wawupay.model.impl

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.bean.remote.account.Register
import com.hviewtech.wawupay.bean.remote.account.VerCode
import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.bean.remote.map.CategoryList
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.ApiService
import com.hviewtech.wawupay.model.ApiModel
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class ApiModelImpl @Inject constructor(internal var mApiService: ApiService) : ApiModel {


  override fun  postUserRegister(
    nickname: String?, firstName: String?, lastName: String?, email: String?, phone: String?, loginPassword: String?
  ): Flowable<BaseResult<Register?>> {
    val obj = HashMap<String, Any?>()
    obj["nickname"] = nickname
    obj["firstName"] = firstName
    obj["lastName"] = lastName
    obj["email"] = email
    obj["phone"] = phone
    obj["loginPassword"] = loginPassword
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mApiService.postUserRegister(body)
  }

  override fun  postGetProviceCitys(): Flowable<BaseResult<ProvinceCitys?>> {
    return mApiService.postGetProviceCitys()
  }

  override fun  postSendVerCode(phone: String?): Flowable<BaseResult<VerCode?>> {
    return mApiService.postSendVerCode(phone)
  }

  override fun  postVerificationPhone(phone: String?, code: String?): Flowable<BaseResult<Any?>> {
    return mApiService.postVerificationPhone(phone, code)
  }

  override fun  postModifyPassword(
    phone: String?, verCode: String?, password: String?, passwordType: Int, customerType: Int
  ): Flowable<BaseResult<Any??>> {
    val obj = HashMap<String, Any?>()
    obj["phone"] = phone
    obj["verCode"] = verCode
    obj["password"] = password
    obj["passwordType"] = passwordType
    obj["customerType"] = customerType
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mApiService.postModifyPassword(body)
  }

  override fun  postUserLogin(accountNum: String?, validate: String?): Flowable<BaseResult<Login?>> {
    val obj = HashMap<String, Any?>()
    obj["accountNum"] = accountNum
    obj["validate"] = validate
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mApiService.postUserLogin(body)
  }

  override fun  postUserLoginByToken(token: String): Flowable<BaseResult<Login?>> {
    return mApiService.postUserLoginByToken(token)
  }

  override fun  postUserIsRealRegister(num: String?): Flowable<BaseResult<Any?>> {
    return mApiService.postUserIsRealRegister(num)
  }

  override fun  postUserCertificateUpload(
    num: String?, idType: Int, idNo: String?, realName: String?, idFrontPic: String?, idBackPic: String?
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["num"] = num
    obj["idType"] = idType
    obj["idNo"] = idNo
    obj["realName"] = realName
    obj["idFrontPic"] = idFrontPic
    obj["idBackPic"] = idBackPic
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mApiService.postUserCertificateUpload(body)
  }

  override fun  postAddBankCard(
    userType: Int, num: String?, realName: String?, bankName: String?, cardNo: String?
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["userType"] = userType
    obj["num"] = num
    obj["realName"] = realName
    obj["bankName"] = bankName
    obj["cardNo"] = cardNo
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mApiService.postAddBankCard(body)
  }

  override fun  postDelBankCard(bankCardId: Int): Flowable<BaseResult<Any?>> {
    return mApiService.postDelBankCard(bankCardId)
  }

  override fun  postBankCardList(userType: Int, num: String?): Flowable<BaseResult<BankCardList?>> {
    val obj = HashMap<String, Any?>()
    obj["userType"] = userType
    obj["num"] = num
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)

    return mApiService.postBankCardList(body)
  }

  override fun  postPayPasswordIsExist(userType: Int, num: String?): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["userType"] = userType
    obj["num"] = num
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)

    return mApiService.postPayPasswordIsExist(body)
  }

  override fun postGetShopCategories(): Flowable<BaseResult<CategoryList?>> {
    return mApiService.postGetShopCategories()
  }

}
