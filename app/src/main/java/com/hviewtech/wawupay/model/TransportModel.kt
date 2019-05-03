package com.hviewtech.wawupay.model

import com.hviewtech.wawupay.bean.remote.BaseDataResult
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.BaseTicketResult
import com.hviewtech.wawupay.bean.remote.transport.*
import io.reactivex.Flowable

interface TransportModel {


  //Authentication/取token
  fun getAuthInfo(userName: String, password: String): Flowable<BaseResult<AuthInfo?>>

  //Get departures sites（取出发点）
  fun getDepartures(token: String): Flowable<BaseDataResult<List<Site>?>>


  //GetDestinationSites(取目的点)
  fun getDestinations(departureId: Int, token: String): Flowable<BaseDataResult<List<Site>?>>


  //CheckDestination/取出发和目前点
  fun queryTickets(
    departure: String,
    destination: String,
    travelDate: String,
    travelTime: String,
    token: String
  ): Flowable<BaseDataResult<List<SiteInfo>?>>


  //Ticket/订票
  fun orderTicket(
    token: String, name: String,
    phone: String, email: String,
    gender: Int, destination_id: String,
    seat: Int, travel_time: String,
    travel_date: String, userId: String

  ): Flowable<BaseTicketResult<TicketInfo?>>


  //Cancel ticket sale/退票
  fun cancelTicket(token: String, id: String): Flowable<BaseDataResult<RefundInfo?>>


  fun queryOrdersRecord(count: Int, page: Int, userId: String): Flowable<BaseDataResult<List<Order>?>>

}