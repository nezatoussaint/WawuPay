package com.hviewtech.wawupay.model

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.utility.*
import io.reactivex.Flowable
import java.math.BigDecimal

interface UtilityModel {


  // 充话费订单
  fun getTopupOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<TopupOrders?>>


  // 充话费
  fun createTopupOrder(
    userId: String,
    phoneNumber: String,
    companyName: String,
    amount: BigDecimal
  ): Flowable<BaseResult<TopupCharge?>>


  // 请求买水
  fun createWaterOffer(userId: String, amount: BigDecimal, meterNumber: String): Flowable<BaseResult<WaterCharge?>>


  // 确认买水
  fun createWaterOrder(
    userId: String,
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    volume: Int
  ): Flowable<BaseResult<Any?>>


  //获取买水订单
  fun getWaterOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<WaterOrders?>>


  // 获取充电费订单
  fun getElectricityOrders(userId: String, page: Int, count: Int): Flowable<BaseResult<ElectricityOrders?>>


  // 请求充电
  fun createElectricityOffer(userId: String, amount: BigDecimal, meterNumber: String): Flowable<BaseResult<ElectricityCharge?>>


  // 确认充电
  fun createElectricityOrder(
    userId: String,
    amount: BigDecimal,
    meterNumber: String,
    meterHolder: String,
    orderNo: String,
    kwt: Int
  ): Flowable<BaseResult<Any?>>


}