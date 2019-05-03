package com.hviewtech.wawupay.di.module.http

import com.hviewtech.wawupay.contract.account.LoginContract
import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.impl.ApiModelImpl
import com.hviewtech.wawupay.presenter.account.LoginPresenter
import dagger.Binds
import dagger.Module

/**
 * @author Eric
 * @description
 * @date 18-3-3
 */
@Module
interface ApiModule {

  @ActivityScope
  @Binds
  fun bindModel(model: ApiModelImpl): ApiModel


  @ActivityScope
  @Binds
  fun bindPresenter(presenter: LoginPresenter): LoginContract.Presenter
}
