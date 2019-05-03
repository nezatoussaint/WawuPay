package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.UserModel
import com.hviewtech.wawupay.model.impl.UserModelImpl
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface UserModule {


  @ActivityScope
  @Binds
  fun bindModel(model: UserModelImpl): UserModel


}
