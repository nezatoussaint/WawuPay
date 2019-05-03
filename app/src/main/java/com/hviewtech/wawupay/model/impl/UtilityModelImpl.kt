package com.hviewtech.wawupay.model.impl

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.utility.*
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.UtilityService
import com.hviewtech.wawupay.model.UtilityModel
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class UtilityModelImpl @Inject constructor(private val mUtilityService: UtilityService) : UtilityModel {
  override fun getTopupOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<TopupOrders?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["page"] = page
    obj["count"] = count
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.getTopupOrders(body)
  }

  override fun createTopupOrder(
    userId: String,
    phoneNumber: String,
    companyName: String,
    amount: BigDecimal
  ): Flowable<BaseResult<TopupCharge?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["phoneNumber"] = phoneNumber
    obj["companyName"] = companyName
    obj["amount"] = amount
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.createTopupOrder(body)
  }

  override fun createWaterOffer(userId: String, amount: BigDecimal, meterNumber: String): Flowable<BaseResult<WaterCharge?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["amount"] = amount
    obj["meterNumber"] = meterNumber
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.createWaterOffer(body)
  }

  override fun createWaterOrder(
    userId: String,
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    volume: Int
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["amount"] = amount
    obj["meterNumber"] = meterNumber
    obj["meterHolder"] = meterHolder
    obj["orderNo"] = orderNo
    obj["volume"] = volume
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.createWaterOrder(body)
  }

  override fun getWaterOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<WaterOrders?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["page"] = page
    obj["count"] = count
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.getWaterOrders(body)
  }

  override fun getElectricityOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<ElectricityOrders?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["page"] = page
    obj["count"] = count
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.getElectricityOrders(body)
  }

  override fun createElectricityOffer(
    userId: String,
    amount: BigDecimal,
    meterNumber: String
  ): Flowable<BaseResult<ElectricityCharge?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["amount"] = amount
    obj["meterNumber"] = meterNumber
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.createElectricityOffer(body)
  }

  override fun createElectricityOrder(
    userId: String,
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    kwt: Int
  ): Flowable<BaseResult<Any?>> {
    val obj = HashMap<String, Any?>()
    obj["userId"] = userId
    obj["amount"] = amount
    obj["meterNumber"] = meterNumber
    obj["meterHolder"] = meterHolder
    obj["orderNo"] = orderNo
    obj["kwt"] = kwt
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return mUtilityService.createElectricityOrder(body)
  }
}