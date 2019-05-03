package com.hviewtech.wawupay.di

import com.hviewtech.wawupay.di.module.http.ApiModule
import com.hviewtech.wawupay.di.scope.FragmentScope
import com.hviewtech.wawupay.ui.fragment.wallet.BankCardFragment
import com.hviewtech.wawupay.ui.fragment.wallet.BankManageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(includes = [ApiModule::class])
interface BankCardModule {


  @FragmentScope
  @ContributesAndroidInjector
  fun bankCard(): BankCardFragment


  @FragmentScope
  @ContributesAndroidInjector
  fun bankManage(): BankManageFragment


}