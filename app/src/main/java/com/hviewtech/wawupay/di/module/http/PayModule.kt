package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.PayModel
import com.hviewtech.wawupay.model.impl.PayModelImpl
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface PayModule {

  @ActivityScope
  @Binds
  fun bindModel(model: PayModelImpl): PayModel

}
