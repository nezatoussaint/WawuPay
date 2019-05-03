package com.hviewtech.wawupay.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * @author su
 * @date 2018/11/23
 * @description 使用Binds  类必须abstract
 */
@Module
abstract class ApplicationModule {

  @Binds
  abstract fun bindContext(application: Application): Context
}