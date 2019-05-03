package com.hviewtech.wawupay.di.module

import com.hviewtech.wawupay.di.BankCardModule
import com.hviewtech.wawupay.di.PaymentDetailsModule
import com.hviewtech.wawupay.di.module.http.*
import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.ui.activity.account.ForgetPasswordActivity
import com.hviewtech.wawupay.ui.activity.account.IdentityProfileActivity
import com.hviewtech.wawupay.ui.activity.account.LoginActivity
import com.hviewtech.wawupay.ui.activity.account.RegisterActivity
import com.hviewtech.wawupay.ui.activity.electricity.ElectricityActivity
import com.hviewtech.wawupay.ui.activity.electricity.ElectricityOrdersActivity
import com.hviewtech.wawupay.ui.activity.home.MainActivity
import com.hviewtech.wawupay.ui.activity.map.DiscoverActivity
import com.hviewtech.wawupay.ui.activity.profile.MyTransactionsActivity
import com.hviewtech.wawupay.ui.activity.profile.PersonalProfileActivity
import com.hviewtech.wawupay.ui.activity.profile.ReportActivity
import com.hviewtech.wawupay.ui.activity.qrcode.QRCodeActivity
import com.hviewtech.wawupay.ui.activity.ticket.TicketBookingActivity
import com.hviewtech.wawupay.ui.activity.ticket.TicketOrdersActivity
import com.hviewtech.wawupay.ui.activity.ticket.TicketSearchActivity
import com.hviewtech.wawupay.ui.activity.ticket.TicketsListActivity
import com.hviewtech.wawupay.ui.activity.topup.TopupActivity
import com.hviewtech.wawupay.ui.activity.topup.TopupOrdersActivity
import com.hviewtech.wawupay.ui.activity.wallet.*
import com.hviewtech.wawupay.ui.activity.water.WaterActivity
import com.hviewtech.wawupay.ui.activity.water.WaterOrdersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
@Module
interface ActivityModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class, TransportModule::class])
  fun login(): LoginActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class])
  fun register(): RegisterActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class, ImgModule::class])
  fun identityProfile(): IdentityProfileActivity


  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class])
  fun forgetPassword(): ForgetPasswordActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class, ImgModule::class, PayModule::class, UserModule::class])
  fun main(): MainActivity


  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class, UserModule::class])
  fun discover(): DiscoverActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [ApiModule::class, UserModule::class])
  fun personalProfile(): PersonalProfileActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class])
  fun payment(): PaymentActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class])
  fun balance(): BalanceActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class, BankCardModule::class])
  fun bankCard(): BankCardActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class, ApiModule::class])
  fun recharge(): RechargeActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class, ApiModule::class])
  fun withdrawDeposit(): WithdrawDepositActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PayModule::class])
  fun setAmount(): SetAmountActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [PaymentDetailsModule::class])
  fun paymentDetails(): PaymentDetailsActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [TransportModule::class])
  fun ticketSearch(): TicketSearchActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [TransportModule::class, PayModule::class])
  fun orders(): TicketOrdersActivity


  @ActivityScope
  @ContributesAndroidInjector(modules = [TransportModule::class])
  fun ticketsList(): TicketsListActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [TransportModule::class, PayModule::class])
  fun userInfomation(): TicketBookingActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UserModule::class])
  fun qrcode(): QRCodeActivity


  @ActivityScope
  @ContributesAndroidInjector(modules = [UserModule::class])
  fun myTransactions(): MyTransactionsActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UserModule::class])
  fun report(): ReportActivity


  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class, PayModule::class])
  fun electricity(): ElectricityActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class, PayModule::class])
  fun water(): WaterActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class, PayModule::class])
  fun topup(): TopupActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class])
  fun electricityOrders(): ElectricityOrdersActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class])
  fun phoneOrders(): TopupOrdersActivity

  @ActivityScope
  @ContributesAndroidInjector(modules = [UtilityModule::class])
  fun waterOrders(): WaterOrdersActivity


}