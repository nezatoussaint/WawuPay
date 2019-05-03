@file:Suppress("DEPRECATION")

package com.hviewtech.wawupay.common.app

import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@GlideModule
class ApplicationGlideModule : com.bumptech.glide.module.AppGlideModule() {

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    //获取内存的默认配置
    //        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
    //        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
    //        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
    //        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
    //        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
    //        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
    //        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

    //内存缓存相关,默认是24m
    val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
    builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))


    //设置磁盘缓存及其路径
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    //super.registerComponents(context, glide, registry);
    //配置glide网络加载框架
    //首先创建OkHttpClient.build进行信任所有证书配置
    val okhttpClient = OkHttpClient().newBuilder()
    //信任所有服务器地址
    okhttpClient.hostnameVerifier { _, _ ->
      //设置为true
      true
    }
    //创建管理器
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
      @SuppressLint("TrustAllX509TrustManager")
      @Throws(java.security.cert.CertificateException::class)
      override fun checkClientTrusted(
        x509Certificates: Array<java.security.cert.X509Certificate>,
        s: String
      ) {
      }

      @SuppressLint("TrustAllX509TrustManager")
      @Throws(java.security.cert.CertificateException::class)
      override fun checkServerTrusted(
        x509Certificates: Array<java.security.cert.X509Certificate>, s: String
      ) {
      }

      override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
        return arrayOf()
      }
    })
    try {
      val sslContext = SSLContext.getInstance("TLS")
      sslContext.init(null, trustAllCerts, java.security.SecureRandom())

      //为OkHttpClient设置sslSocketFactory
      okhttpClient.sslSocketFactory(sslContext.socketFactory)

    } catch (e: Exception) {
      e.printStackTrace()
    }


    val factory = OkHttpUrlLoader.Factory(okhttpClient.build())
    registry.replace(GlideUrl::class.java, InputStream::class.java, factory)

  }

  override fun isManifestParsingEnabled(): Boolean {
    //不使用清单配置的方式,减少初始化时间
    return false
  }
}
