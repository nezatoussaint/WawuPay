//package com.hviewtech.wawupay.common.utils
//
//import android.text.TextUtils
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.Types
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import java.lang.reflect.Type
//import kotlin.reflect.KClass
//
//
//@Deprecated("User Moshi")
//object MoshiUtils {
//
//  private var mMoshiBuilder: Moshi.Builder? = null
//
//
//  fun moshi(): Moshi {
//    synchronized(MoshiUtils::class.java) {
//      if (mMoshiBuilder == null) {
//        mMoshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
//      }
//    }
//    return mMoshiBuilder!!.build()
//  }
//
//
//  fun <T : Any> parseObject(json: String, clazz: KClass<T>): T? {
//    return moshi().adapter<T>(clazz.java).fromJson(json)
//  }
//
//  fun <T : Any> parseObject(json: String, type: Type): T? {
//
//    return moshi().adapter<T>(type).fromJson(json)
//  }
//
//
//  fun <T : Any> parseArray(json: String, clazz: KClass<T>): List<T>? {
//    val type = Types.newParameterizedType(List::class.java, clazz.java)
//    val adapter = moshi().adapter<List<T>>(type)
//    return adapter.fromJson(json)
//  }
//
//
//  fun <T : Any> obj2Map(json: String): Map<String, T>? {
//    val type = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
//    return moshi().adapter<Map<String, T>>(type).fromJson(json)
//  }
//
//  fun toJson(obj: Any?): String {
//    if (obj == null) {
//      return "{}"
//    }
//
//    return moshi().adapter<Any>(Any::class.java).toJson(obj)
//
////    return moshi().adapter<Any>(Any::class.java).toJson(obj)
//  }
//
//
//  fun isJsonValid(json: String): Boolean {
//    try {
//      val result = !TextUtils.isEmpty(json)
//      moshi().adapter<Any>(Any::class.java).fromJson(json)
//      return result
//    } catch (e: Exception) {
//      return false
//    }
//
//  }
//
//}