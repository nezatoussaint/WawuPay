package com.hviewtech.wawupay.di.module

import android.content.Context
import com.hviewtech.wawupay.common.ext.loge
import com.hviewtech.wawupay.common.utils.JsonUtils
import com.hviewtech.wawupay.data.http.*
import com.hviewtech.wawupay.data.http.intercept.HttpLogger
import com.hviewtech.wawupay.data.http.intercept.NetInterceptor
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.UserModel
import com.hviewtech.wawupay.model.impl.ApiModelImpl
import com.hviewtech.wawupay.model.impl.UserModelImpl
import dagger.Module
import dagger.Provides
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author su
 * @date 2018/11/23
 * @description 使用Provides  类必须non-abstract 或者static methods
 */
@Module
class HttpModule {

  init {
    RxJavaPlugins.setErrorHandler({
      loge(it)
    });
  }

  private class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
      type: Type, annotations: Array<out Annotation>, retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {

      val converter: Converter<ResponseBody, Any> = retrofit!!.nextResponseBodyConverter<Any>(this, type, annotations)

      return Converter<ResponseBody, Any> { body: ResponseBody ->
        if (body.contentLength() == 0L) {
          null
        } else {
          converter.convert(body)
        }
      }
    }

  }

  //设置缓存目录和缓存空间大小
  @Provides
  @Singleton
  fun provideCache(context: Context): Cache? {
    var cache: Cache? = null
    try {
      cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
    } catch (e: Exception) {
      loge(e)
    }
    return cache
  }


  @Provides
  @Singleton
  fun provideOkHttpClient(cache: Cache?): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor(HttpLogger())
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder().addInterceptor(logInterceptor).addNetworkInterceptor(NetInterceptor()).cache(cache)
      .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
      .connectionPool(ConnectionPool(8, 10, TimeUnit.SECONDS)).build()

  }

  @Provides
  @Singleton
  fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      //设置服务器路径
      .baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //      .addConverterFactory(MoshiConverterFactory.create(MoshiUtils.moshi()).asLenient())
      //设置使用okhttp网络请求
      .client(okHttpClient).build()
  }


  @Provides
  @Singleton
  fun provideApiService(okHttpClient: OkHttpClient, rel: Boolean): ApiService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) ApiService.REL_URL else ApiService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(ApiService::class.java)
  }


  @Provides
  @Singleton
  fun providePayService(okHttpClient: OkHttpClient, rel: Boolean): PayService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) PayService.REL_URL else PayService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(PayService::class.java)
  }

  @Provides
  @Singleton
  fun provideImgService(okHttpClient: OkHttpClient, rel: Boolean): ImgService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) ImgService.REL_URL else ImgService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(ImgService::class.java)
  }

  @Provides
  @Singleton
  fun provideUserService(okHttpClient: OkHttpClient, rel: Boolean): UserService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) UserService.REL_URL else UserService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(UserService::class.java)
  }

  @Provides
  @Singleton
  fun provideTransportService(okHttpClient: OkHttpClient, rel: Boolean): TransportService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) TransportService.REL_URL else TransportService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(TransportService::class.java)
  }

  @Provides
  @Singleton
  fun provideUtilityService(okHttpClient: OkHttpClient, rel: Boolean): UtilityService {
    val builder = Retrofit.Builder()
      //设置服务器路径
      .baseUrl(if (rel) UtilityService.REL_URL else UtilityService.DEV_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(NullOnEmptyConverterFactory())
      .addConverterFactory(GsonConverterFactory.create(JsonUtils.gson()))
      //设置使用okhttp网络请求
      .client(okHttpClient)

    return builder.build().create(UtilityService::class.java)
  }

  @Provides
  @Singleton
  fun provideRelEnv(): Boolean {
    return true
  }

  companion object {
    private var apiModel: ApiModel? = null
    private var userModel: UserModel? = null
    fun apiModel(context: Context): ApiModel {
      if (apiModel == null) {
        val module = HttpModule()
        val cache = module.provideCache(context)
        val client = module.provideOkHttpClient(cache)
        val apiService = module.provideApiService(client, module.provideRelEnv())
        apiModel = ApiModelImpl(apiService)
      }
      return apiModel!!

    }

    fun userModel(context: Context): UserModel {
      if (userModel == null) {
        val module = HttpModule()
        val cache = module.provideCache(context)
        val client = module.provideOkHttpClient(cache)
        val userService = module.provideUserService(client, module.provideRelEnv())
        userModel = UserModelImpl(userService)
      }
      return userModel!!

    }
  }
}
