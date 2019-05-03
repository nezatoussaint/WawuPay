package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.UtilityModel
import com.hviewtech.wawupay.model.impl.UtilityModelImpl
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface UtilityModule {


  @ActivityScope
  @Binds
  fun bindModel(model: UtilityModelImpl): UtilityModel


}
