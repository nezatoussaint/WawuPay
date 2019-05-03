package com.hviewtech.wawupay

import android.content.Context
import android.support.multidex.MultiDex
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.app.SmartRefreshManager
import com.hviewtech.wawupay.common.app.TimberManager
import com.hviewtech.wawupay.common.storage.AESEncryption
import com.hviewtech.wawupay.common.storage.SharedPreferencesStorage
import com.hviewtech.wawupay.common.utils.AppUtils
import com.hviewtech.wawupay.di.component.DaggerAppComponent
import com.just.agentweb.AgentWebConfig
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import me.jessyan.autosize.AutoSizeConfig
import top.bingoz.swipebacklib.SlideFinishManager
import java.util.*


/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class AppApplication : DaggerApplication() {


  companion object {
    lateinit var app: AppApplication

    fun getDrawableUri(resId: Int): String {
      return Constants.Prefix.DRAWABLE + app.resources.getResourceEntryName(resId)
    }
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(base)
  }

  override fun onCreate() {
    super.onCreate()
    app = this
    AppUtils.loadAppLanguage(this, Locale.ENGLISH)

    /**
     * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
     * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
     */
    //        AutoSizeConfig.getInstance()

    //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
    //                .setLog(false)
    //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
    //AutoSize 会将屏幕总高度减去状态栏高度来做适配, 如果设备上有导航栏还会减去导航栏的高度
    //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏以及导航栏高度
    //                .setUseDeviceSize(true)

    AutoSizeConfig.getInstance()
      .setCustomFragment(true)
      .setLog(BuildConfig.LOG_DEBUG)
      .unitsManager
      .setSupportDP(true)
      .setSupportSP(true)

    ViewPump.init(
      ViewPump.builder().addInterceptor(
        CalligraphyInterceptor(
          CalligraphyConfig.Builder()
//            .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
        )
      )
        .build()
    )

    TimberManager.initialize()
    SlideFinishManager.getInstance().init(this)

    if (BuildConfig.APP_DEBUG) {
      AgentWebConfig.debug()
    }
    Hawk.init(this)
      .setEncryption(AESEncryption())
      .setStorage(SharedPreferencesStorage(this))
      .build()

    SmartRefreshManager.initialize(this)

  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder().application(this).build()
  }


}