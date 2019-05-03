package com.hviewtech.wawupay.model.impl

import com.google.gson.JsonObject
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.UserService
import com.hviewtech.wawupay.model.UserModel
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import javax.inject.Inject

class UserModelImpl @Inject
constructor(private val mUserService: UserService) : UserModel {


  override fun postGetMerchantPosition(
    userNum: String,
    longitude: Double,
    latitude: Double,
    km: Int,
    categoryId: Int
  ): Flowable<BaseResult<MerchantPositionList?>> {
    val obj = HashMap<String, Any>()
    obj["userNum"] = userNum
    obj["longitude"] = longitude
    obj["latitude"] = latitude
    obj["km"] = km

    if (categoryId > 0) {
      obj["categoryId"] = categoryId
    }
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postGetMerchantPosition(body)
  }

  override fun postValidatePayPassword(num: String, payPassword: String): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any>()
    obj["num"] = num
    obj["payPassword"] = payPassword
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postValidatePayPassword(body)
  }

  override fun postModifyPayPassword(
    num: String,
    payPassword: String,
    phone: String,
    verCode: String
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any>()
    obj["num"] = num
    obj["payPassword"] = payPassword
    obj["phone"] = phone
    obj["verCode"] = verCode
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postModifyPayPassword(body)
  }


  override fun requestNet(path: String): Flowable<BaseResult<JsonObject?>> {
    return mUserService.requestNet(path)
  }

  override fun postUserComplaintInfo(
    userId: String,
    title: String,
    content: String,
    orderNo: String
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any>()
    obj["userId"] = userId
    obj["title"] = title
    obj["content"] = content
    obj["orderNo"] = orderNo
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postUserComplaintInfo(body)
  }

  override fun postModifyUserInfo(json: String): Flowable<BaseResult<Any?>> {
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postModifyUserInfo(body)
  }

  override fun postGetUserBillInfoList(userId: String, category: Int, page: Int): Flowable<BaseResult<PaymentDetails?>> {
    val obj = HashMap<String, Any>()
    obj["userId"] = userId
    if (category >= 1 && category <= 8) {
      // 账单分类(1-转账 2-店铺付款 3-在线支付 4-AA账单 5-红包 6-提现 7-退款 8-充值)
      obj["category"] = category
    }
    obj["page"] = page
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUserService.postGetUserBillInfoList(body)
  }
}
