package com.hviewtech.wawupay.common.utils

import android.text.TextUtils
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.util.*

object JsonUtils {

  private var mGsonBuilder: GsonBuilder? = null


  private val BIG_DECIMAL_SERIALIZER = JsonSerializer<BigDecimal> { value, typeOfSrc, context ->
    // Keep 2 decimal digits only
    JsonPrimitive(NumberUtils.multiply100(value))
  }
  private val BIG_DECIMAL_DESERIALIZER = JsonDeserializer { json, typeOfT, context ->
    val decimal = json.asBigDecimal
    NumberUtils.divide(decimal, 100)
  }

  fun gson(): Gson {
    synchronized(JsonUtils::class.java) {
      if (mGsonBuilder == null) {
        mGsonBuilder = GsonBuilder()
          .registerTypeAdapter(BigDecimal::class.java, BIG_DECIMAL_SERIALIZER)
          .registerTypeAdapter(BigDecimal::class.java, BIG_DECIMAL_DESERIALIZER)
      }
    }
    return mGsonBuilder!!.create()
  }


  fun <T> parseObject(json: String, clazz: Class<T>): T {
    return gson().fromJson(json, clazz)
  }

  fun <T> parseObject(json: String, clazz: Type): T {
    return gson().fromJson<T>(json, clazz)
  }

  fun <T> parseArray(json: String, clazz: Class<T>): List<T> {
    val beans = ArrayList<T>()
    //Json的解析类对象
    val parser = JsonParser()
    //将JSON的String 转成一个JsonArray对象
    val array = parser.parse(json).asJsonArray
    //加强for循环遍历JsonArray
    for (user in array) {
      //使用GSON，直接转成Bean对象
      val bean = gson().fromJson(user, clazz)
      beans.add(bean)
    }
    return beans
  }


  fun obj2Map(json: String): Map<String, Any> {
    val type = object : TypeToken<Map<String, Any>>() {

    }.type
    return gson().fromJson<Map<String, Any>>(json, type)
  }

  //    private static ValueFilter filter = new ValueFilter() {
  //        @Override
  //        public Object process(Object object, String name, Object value) {
  //            if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
  //                return new BigDecimal(value.toString());
  //            }
  //            return value;
  //        }
  //    };

  fun obj2Map(obj: Any): Map<String, Any> {
    val json = gson().toJson(obj)
    return obj2Map(json)
  }

  fun toJson(obj: Any?): String {
    if (obj == null) {
      return "{}"
    }
    return gson().toJson(obj)
  }

  fun isJsonValid(json: String): Boolean {
    try {
      val result = !TextUtils.isEmpty(json)
      gson().fromJson(json, Any::class.java)
      return result
    } catch (e: JsonSyntaxException) {
      return false
    }

  }

  fun toJsonObject(json: String): JSONObject? {
    var obj: JSONObject? = null
    try {
      obj = JSONObject(json)
    } catch (ignored: JSONException) {
      try {
        obj = JSONObject("{}")
      } catch (ignore: JSONException) {
      }

    }

    return obj
  }

  fun toJsonArray(json: String): JSONArray? {
    var obj: JSONArray? = null
    try {
      obj = JSONArray(json)
    } catch (ignored: JSONException) {
      try {
        obj = JSONArray("[]")
      } catch (ignore: JSONException) {
      }

    }

    return obj
  }

}