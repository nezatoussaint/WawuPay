package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.TransportModel
import com.hviewtech.wawupay.model.impl.TransportModelImpl
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface TransportModule {


  @ActivityScope
  @Binds
  fun bindModel(model: TransportModelImpl): TransportModel


}
