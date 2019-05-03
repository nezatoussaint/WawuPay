package com.hviewtech.wawupay.data.http.exception

import com.google.gson.JsonParseException
import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.R
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException


object ErrorHandler {

  fun analysisError(e: Throwable): ApiException {

    val exception = ApiException()
    if (e is ApiException) {
      exception.code = e.code
      exception.displayMessage = e.displayMessage
      return exception
    }

    if (e is JsonParseException) {
      exception.code = ApiException.JSON_ERROR
    } else if (e is HttpException) {
      exception.code = e.code()
    } else if (e is SocketTimeoutException) {
      exception.code = ApiException.SOCKET_TIMEOUT_ERROR
    } else if (e is SocketException) {
      exception.code = ApiException.SOCKET_ERROR
    } else if (e is ProxyException) {
      exception.code = ApiException.PROXY_ERROR
    } else {
      exception.code = ApiException.UNKNOWN_ERROR
    }

    exception.displayMessage = create(exception.code)
    return exception
  }

  fun create(code: Int): String {

    val context = AppApplication.app
    val errorMsg: String

    when (code) {
      ApiException.HTTP_ERROR -> errorMsg = context.getResources().getString(R.string.error_http)
      ApiException.SOCKET_TIMEOUT_ERROR -> errorMsg = context.getResources().getString(R.string.error_socket_timeout)
      ApiException.SOCKET_ERROR -> errorMsg = context.getResources().getString(R.string.error_socket_unreachable)
      ApiException.ERROR_HTTP_400 -> errorMsg = context.getResources().getString(R.string.error_http_400)
      ApiException.ERROR_HTTP_404 -> errorMsg = context.getResources().getString(R.string.error_http_404)
      ApiException.ERROR_HTTP_500 -> errorMsg = context.getResources().getString(R.string.error_http_500)
      ApiException.ERROR_API_SYSTEM -> errorMsg = context.getResources().getString(R.string.error_system)

      ApiException.ERROR_API_ACCOUNT_FREEZE -> errorMsg =
          context.getResources().getString(R.string.error_account_freeze)

      ApiException.ERROR_API_NO_PERMISSION -> errorMsg =
          context.getResources().getString(R.string.error_api_no_perission)

      ApiException.ERROR_API_LOGIN -> errorMsg = context.getResources().getString(R.string.error_login)

      ApiException.ERROR_TOKEN -> errorMsg = context.getResources().getString(R.string.error_token)

      else -> errorMsg = context.getResources().getString(R.string.error_unkown)
    }

    return errorMsg

  }
}
