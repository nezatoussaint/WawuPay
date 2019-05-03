package com.hviewtech.wawupay.model.impl

import com.hviewtech.wawupay.bean.remote.BaseDataResult
import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.BaseTicketResult
import com.hviewtech.wawupay.bean.remote.transport.*
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.TransportService
import com.hviewtech.wawupay.model.TransportModel
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import javax.inject.Inject

class TransportModelImpl @Inject constructor(private val ticketService: TransportService) : TransportModel {

  override fun getAuthInfo(userName: String, password: String): Flowable<BaseResult<AuthInfo?>> {
    return ticketService.getAuthInfo(userName, password)
  }

  override fun getDepartures(token: String): Flowable<BaseDataResult<List<Site>?>> {
    return ticketService.getDepartures(token)
  }

  override fun getDestinations(departureId: Int, token: String): Flowable<BaseDataResult<List<Site>?>> {
    return ticketService.getDestinations(departureId, token)
  }

  override fun queryTickets(
    departure: String,
    destination: String,
    travelDate: String,
    travelTime: String,
    token: String
  ): Flowable<BaseDataResult<List<SiteInfo>?>> {
    return ticketService.queryTickets(departure, destination, travelDate, travelTime, token)
  }

  override fun orderTicket(
    token: String,
    name: String,
    phone: String,
    email: String,
    gender: Int,
    destination_id: String,
    seat: Int,
    travel_time: String,
    travel_date: String,
    userId: String
  ): Flowable<BaseTicketResult<TicketInfo?>> {
    return ticketService.orderTicket(
      token,
      name,
      phone,
      email,
      gender,
      destination_id,
      seat,
      travel_time,
      travel_date,
      userId
    )
  }

  override fun cancelTicket(token: String, id: String): Flowable<BaseDataResult<RefundInfo?>> {
    return ticketService.cancelTicket(token, id)
  }

  override fun queryOrdersRecord(
    count: Int,
    page: Int,
    userId: String
  ): Flowable<BaseDataResult<List<Order>?>> {
    val obj = HashMap<String, Any?>()
    obj["count"] = count
    obj["page"] = page
    obj["userId"] = userId
    val json = JsonUtils.toJson(obj)
    val body = RequestBody.create(MediaType.parse(Constants.RequestType.JSON), json)
    return ticketService.queryOrdersRecord(body)
  }

}