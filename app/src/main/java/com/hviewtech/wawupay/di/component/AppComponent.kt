package com.hviewtech.wawupay.di.component

import android.app.Application
import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.di.module.ActivityModule
import com.hviewtech.wawupay.di.module.ApplicationModule
import com.hviewtech.wawupay.di.module.HttpModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
@Singleton
@Component(modules = [HttpModule::class, ApplicationModule::class, ActivityModule::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<AppApplication> {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): AppComponent.Builder

    fun build(): AppComponent
  }
}