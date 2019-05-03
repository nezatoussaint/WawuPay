package com.hviewtech.wawupay.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import android.webkit.WebView
import com.hviewtech.wawupay.AppApplication
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
object DeviceUtils {

  fun getAndroidID(ctx: Context): String {
    return Settings.Secure.getString(ctx.contentResolver, Settings.Secure.ANDROID_ID)
  }

  fun getIP(ctx: Context): String? {
    val wifiManager = ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return if (wifiManager.isWifiEnabled) getWifiIP(wifiManager) else getGPRSIP()
  }

  fun getWifiIP(wifiManager: WifiManager): String {
    val wifiInfo = wifiManager.connectionInfo
    val ip = intToIp(wifiInfo.ipAddress)
    return ip
  }

  fun getGPRSIP(): String? {
    var ip: String? = null
    try {
      val en = NetworkInterface.getNetworkInterfaces()
      while (en.hasMoreElements()) {
        val enumIpAddr = en.nextElement().inetAddresses
        while (enumIpAddr.hasMoreElements()) {
          val inetAddress = enumIpAddr.nextElement()
          if (!inetAddress.isLoopbackAddress) {
            ip = inetAddress.hostAddress
          }
        }
      }
    } catch (e: SocketException) {
      e.printStackTrace()
      ip = null
    }

    return ip
  }

  private fun intToIp(i: Int): String {
    return (i and 0xFF).toString() + "." + (i shr 8 and 0xFF) + "." + (i shr 16 and 0xFF) + "." + (i shr 24 and 0xFF)
  }

  fun getCarrier(ctx: Context): String {
    val tm = ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return tm.networkOperatorName.toLowerCase(Locale.getDefault())
  }


  fun getModel(): String {
    return Build.MODEL
  }

  fun getBuildBrand(): String {
    return Build.BRAND
  }

  fun getBuildHost(): String {
    return Build.HOST
  }

  fun getBuildTags(): String {
    return Build.TAGS
  }

  fun getBuildTime(): Long {
    return Build.TIME
  }

  fun getBuildUser(): String {
    return Build.USER
  }

  fun getBuildVersionRelease(): String {
    return Build.VERSION.RELEASE
  }

  fun getBuildVersionCodename(): String {
    return Build.VERSION.CODENAME
  }

  fun getBuildVersionIncremental(): String {
    return Build.VERSION.INCREMENTAL
  }

  fun getBuildVersionSDK(): Int {
    return Build.VERSION.SDK_INT
  }

  fun getBuildID(): String {
    return Build.ID
  }

  fun getSupportedABIS(): Array<String> {
    var result: Array<String>? = arrayOf("-")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      result = Build.SUPPORTED_ABIS
    }
    if (result == null || result.size == 0) {
      result = arrayOf("-")
    }
    return result
  }

  fun getManufacturer(): String {
    return Build.MANUFACTURER
  }


  fun getBootloader(): String {
    return Build.BOOTLOADER
  }


  fun getScreenDisplayID(ctx: Context): String {
    val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return wm.defaultDisplay.displayId.toString()
  }

  fun getDisplayVersion(): String {
    return Build.DISPLAY
  }


  fun getLanguage(): String {
    return Locale.getDefault().language
  }

  fun getCountry(ctx: Context): String {
    val tm = ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val locale = Locale.getDefault()
    return if (tm.simState == TelephonyManager.SIM_STATE_READY) tm.simCountryIso.toLowerCase(Locale.getDefault()) else locale.country.toLowerCase(
      locale
    )
  }

  fun getOSVersion(): String {
    return Build.VERSION.RELEASE
  }

  //<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
  fun getGSFID(context: Context): String? {
    val result: String
    val URI = Uri.parse("content://com.google.android.gsf.gservices")
    val ID_KEY = "android_id"
    val params = arrayOf(ID_KEY)
    val c = context.contentResolver.query(URI, null, null, params, null)
    if (c == null || !c.moveToFirst() || c.columnCount < 2) {
      return null
    } else {
      result = java.lang.Long.toHexString(java.lang.Long.parseLong(c.getString(1)))
    }
    c.close()
    return result
  }

  @Suppress("DEPRECATION")
  fun getPsuedoUniqueID(): String {
    var devIDShort = "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      devIDShort += Build.SUPPORTED_ABIS[0].length % 10
    } else {
      devIDShort += Build.CPU_ABI.length % 10
    }
    devIDShort += Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
    var serial: String
    try {
      serial = Build::class.java.getField("SERIAL").get(null).toString()
      return UUID(devIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
    } catch (e: Exception) {
      serial = "ESYDV000"
    }

    return UUID(devIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
  }

  fun getFingerprint(): String {
    return Build.FINGERPRINT
  }

  fun getHardware(): String {
    return Build.HARDWARE
  }

  fun getProduct(): String {
    return Build.PRODUCT
  }

  fun getDevice(): String {
    return Build.DEVICE
  }

  fun getBoard(): String {
    return Build.BOARD
  }

  fun getRadioVersion(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) Build.getRadioVersion() else ""
  }

  fun getUA(ctx: Context): String {
    val system_ua = System.getProperty("http.agent")
    return WebView(ctx).settings.userAgentString + "__" + system_ua
  }

  fun getDensity(ctx: Context): String? {
    var densityStr: String? = null
    val density = ctx.resources.displayMetrics.densityDpi
    when (density) {
      DisplayMetrics.DENSITY_LOW -> densityStr = "LDPI"
      DisplayMetrics.DENSITY_MEDIUM -> densityStr = "MDPI"
      DisplayMetrics.DENSITY_TV -> densityStr = "TVDPI"
      DisplayMetrics.DENSITY_HIGH -> densityStr = "HDPI"
      DisplayMetrics.DENSITY_XHIGH -> densityStr = "XHDPI"
      DisplayMetrics.DENSITY_400 -> densityStr = "XMHDPI"
      DisplayMetrics.DENSITY_XXHIGH -> densityStr = "XXHDPI"
      DisplayMetrics.DENSITY_XXXHIGH -> densityStr = "XXXHDPI"
    }
    return densityStr
  }

  fun isWifiProxy(): Boolean {
    val proxyAddress: String? = System.getProperty("http.proxyHost")
    val portStr: String? = System.getProperty("http.proxyPort")
    val proxyPort: Int? = Integer.parseInt(portStr ?: "-1")
    return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1 && proxyPort != 8888 && proxyPort != 8889
  }

  @SuppressLint("MissingPermission")
  fun getDeviceId(): String {
    return (AppApplication.app.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
  }
}