//package com.hviewtech.wawupay.common.utils
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.support.v4.app.Fragment
//
///**
// * @author su
// * @date 2018/11/23
// * @description
// */
//class LoginUtils : Activity() {
//    val LOGIN_CODE = 1024
//
//    companion object {
//        var mCallback: () -> Unit? = {}
//
//        fun isLogin(context: Context, callback: () -> Unit) {
//            val login = AccountUtil.getAccount().isLogin
//            if (login) {
//                callback.invoke()
//            } else {
//                mCallback = callback
//                context.router(RouterImpl.LoginUtils)
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        routerForResult(RouterImpl.LoginActivity, LOGIN_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        finish()
//        if (resultCode == Activity.RESULT_OK) {
//            val login = AccountUtil.getAccount().isLogin
//            if (login) {
//                mCallback.invoke()
//            }
//        }
//        mCallback = {}
//    }
//}
//
//fun Context.isLogin(): Boolean = AccountUtil.getAccount().isLogin
//
//fun Context.checkLogin(callback: () -> Unit, check: Boolean = true) {
//    if (check)
//        LoginUtils.isLogin(this, callback)
//    else
//        callback.invoke()
//}
//
//fun Context.routerLogin(toPage: String, vararg pairs: Pair<*, *>) {
//    checkLogin({ router(toPage, *pairs) })
//}
//
//fun Activity.routerForResultLogin(toPage: String, requestCode: Int, vararg pairs: Pair<*, *>) {
//    checkLogin({ routerForResult(toPage, requestCode, *pairs) })
//}
//
//fun Fragment.routerLogin(toPage: String, vararg pairs: Pair<*, *>) {
//    activity?.checkLogin({ router(toPage, *pairs) })
//}
//
//private fun getParamString(pairs: Array<out Pair<*, *>>): String {
//    var param: String = ""
//    pairs.forEach {
//        param = param.plus("${it.first}=${it.second}&")
//    }
//
//    if (param.isNotBlank()) {
//        param = "?${param.substring(0, param.length - 1)}"
//    }
//    return param
//}