package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.ImgModel
import com.hviewtech.wawupay.model.impl.ImgModelImpl
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface ImgModule {

  @ActivityScope
  @Binds
  fun bindModel(model: ImgModelImpl): ImgModel

}
