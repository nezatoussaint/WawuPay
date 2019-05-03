package com.hviewtech.wawupay.data.http.exception

/**
 * 回调统一请求异常
 */

class ApiException : Exception {


  var code: Int = 0

  var displayMessage: String? = null

  constructor() {}

  constructor(code: Int, displayMessage: String) : super(displayMessage) {
    this.code = code
    this.displayMessage = displayMessage
  }

  companion object {

    /*API错误*/
    val API_ERROR = 0x0

    /*网络错误*/
    val NETWORD_ERROR = 0x1
    /*http_错误*/
    val HTTP_ERROR = 0x2
    /*json错误*/
    val JSON_ERROR = 0x3
    /*未知错误*/
    val UNKNOWN_ERROR = 0x4
    /*运行时异常-包含自定义异常*/
    val RUNTIME_ERROR = 0x5
    /*无法解析该域名*/
    val UNKOWNHOST_ERROR = 0x6

    /*连接网络超时*/
    val SOCKET_TIMEOUT_ERROR = 0x7

    /*无网络连接*/
    val SOCKET_ERROR = 0x8
    /*代理*/
    val PROXY_ERROR = 0x9


    // 服务器错误
    val ERROR_API_SYSTEM = 10000

    // 登录错误，用户名密码错误
    val ERROR_API_LOGIN = 10001

    //调用无权限的API
    val ERROR_API_NO_PERMISSION = 10002

    //账户被冻结
    val ERROR_API_ACCOUNT_FREEZE = 10003

    //Token 失效
    val ERROR_TOKEN = 10010
    // http

    val ERROR_HTTP_400 = 400

    val ERROR_HTTP_404 = 404

    val ERROR_HTTP_405 = 405

    val ERROR_HTTP_500 = 500
  }

}

