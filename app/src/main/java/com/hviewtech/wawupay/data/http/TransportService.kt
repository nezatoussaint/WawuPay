package com.hviewtech.wawupay.data.http

import com.hviewtech.wawupay.bean.remote.BaseDataResult
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.BaseTicketResult
import com.hviewtech.wawupay.bean.remote.transport.*
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransportService {
  companion object {
    const val DEV_URL = "http://167.99.89.30:8087"
    const val REL_URL = "http://167.99.89.30:8087"
  }


  //Authentication/取token
  @GET("/eticketcore/auth/?r=auth/thirdParty")
  fun getAuthInfo(@Query("userName") userName: String, @Query("password") password: String): Flowable<BaseResult<AuthInfo?>>

  //Get departures sites（取出发点）
  @GET("/eticketcore/getDepartures?r=api/sites")
  fun getDepartures(@Query("token") token: String): Flowable<BaseDataResult<List<Site>?>>


  //GetDestinationSites(取目的点)
  @GET("/eticketcore/getDestinations?r=api/sites")
  fun getDestinations(@Query("departureId") departureId: Int, @Query("token") token: String): Flowable<BaseDataResult<List<Site>?>>


  //CheckDestination/取出发和目前点
  @GET("/eticketcore/tickets?r=api/ticket")
  fun queryTickets(
    @Query("departure") departure: String, @Query("destination") destination: String, @Query("travelDate") travelDate: String, @Query(
      "travelTime"
    ) travelTime: String, @Query("token") token: String
  ): Flowable<BaseDataResult<List<SiteInfo>?>>


  //Ticket/订票
  @GET("/eticketcore/orderTicket/?r=api/ticket")
  fun orderTicket(
    @Query("token") token: String, @Query("name") name: String,
    @Query("phone") phone: String, @Query("email") email: String,
    @Query("gender") gender: Int, @Query("destination_id") destination_id: String,
    @Query("seat") seat: Int, @Query("travel_time") travel_time: String,
    @Query("travel_date") travel_date: String, @Query("userId") userId: String

  ): Flowable<BaseTicketResult<TicketInfo?>>


  //Cancel ticket sale/退票
  @GET("/eticketcore/cancel/?r=api/cancelTicketSale")
  fun cancelTicket(@Query("token") token: String, @Query("id") id: String): Flowable<BaseDataResult<RefundInfo?>>


  @POST("/eticketcore/userTickets")
  fun queryOrdersRecord(@Body body: RequestBody): Flowable<BaseDataResult<List<Order>?>>
}