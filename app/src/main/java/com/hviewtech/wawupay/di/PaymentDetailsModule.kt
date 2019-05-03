package com.hviewtech.wawupay.di

import com.hviewtech.wawupay.di.module.http.UserModule
import com.hviewtech.wawupay.di.scope.FragmentScope
import com.hviewtech.wawupay.ui.fragment.wallet.PaymentDetailFragment
import com.hviewtech.wawupay.ui.fragment.wallet.PaymentDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(includes = [UserModule::class])
interface PaymentDetailsModule {


  @FragmentScope
  @ContributesAndroidInjector
  fun paymentDetails(): PaymentDetailsFragment

  @FragmentScope
  @ContributesAndroidInjector
  fun paymentDetail(): PaymentDetailFragment
}