package com.hviewtech.wawupay.data.http

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.utility.*
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UtilityService {


  companion object {
    const val DEV_URL = "http://167.99.89.30:8087"
    const val REL_URL = "http://167.99.89.30:8087"
  }


  // 充话费订单
  @POST("/thirdParty/totup/getOrders")
  fun getTopupOrders(@Body body: RequestBody): Flowable<BaseResult<TopupOrders?>>


  // 充话费
  @POST("/thirdParty/totup/createOrder")
  fun createTopupOrder(@Body body: RequestBody): Flowable<BaseResult<TopupCharge?>>


  // 请求买水
  @POST("/thirdParty/water/createOffer")
  fun createWaterOffer(@Body body: RequestBody): Flowable<BaseResult<WaterCharge?>>


  // 确认买水
  @POST("/thirdParty/water/createOrder")
  fun createWaterOrder(@Body body: RequestBody): Flowable<BaseResult<Any?>>


  //获取买水订单
  @POST("/thirdParty/water/getOrders")
  fun getWaterOrders(@Body body: RequestBody): Flowable<BaseResult<WaterOrders?>>


  // 获取充电费订单
  @POST("/thirdParty/electricity/getOrders")
  fun getElectricityOrders(@Body body: RequestBody): Flowable<BaseResult<ElectricityOrders?>>


  // 请求充电
  @POST("/thirdParty/electricity/createOffer")
  fun createElectricityOffer(@Body body: RequestBody): Flowable<BaseResult<ElectricityCharge?>>


  // 确认充电
  @POST("/thirdParty/electricity/createOrder")
  fun createElectricityOrder(@Body body: RequestBody): Flowable<BaseResult<Any?>>

}