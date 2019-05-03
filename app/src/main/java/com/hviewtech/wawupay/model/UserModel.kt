package com.hviewtech.wawupay.model

import com.google.gson.JsonObject
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetails
import io.reactivex.Flowable

interface UserModel {

  fun postGetMerchantPosition(
    userNum: String,
    longitude: Double,
    latitude: Double,
    km: Int,
    categoryId: Int
  ): Flowable<BaseResult<MerchantPositionList?>>


  fun postValidatePayPassword(
    num: String,
    payPassword: String
  ): Flowable<BaseResult<Any?>>


  fun postModifyPayPassword(
    num: String,
    payPassword: String,
    phone: String,
    verCode: String
  ): Flowable<BaseResult<Any?>>


  fun requestNet(
    path: String
  ): Flowable<BaseResult<JsonObject?>>

  fun postUserComplaintInfo(
    userId: String,
    title: String,
    content: String,
    orderNo: String
  ): Flowable<BaseResult<Any?>>

  fun postModifyUserInfo(
    json: String
  ): Flowable<BaseResult<Any?>>


  fun postGetUserBillInfoList(
    userId: String, //        用户ID
    category: Int, //     账单分类(1-转账 2-店铺付款 3-在线支付 4-AA账单 5-红包 6-提现 7-退款 8-充值)  (非必填)
    page: Int     //     页数
  ): Flowable<BaseResult<PaymentDetails?>>
}
