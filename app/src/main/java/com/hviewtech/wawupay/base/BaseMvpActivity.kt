package com.hviewtech.wawupay.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hviewtech.wawupay.common.utils.InjectUtils
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class BaseMvpActivity : BaseActivity(), HasSupportFragmentInjector {

  @Inject
  lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

  private var PRESENTER = MultiPresenter()

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return supportFragmentInjector
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    // 因为父类的initialize方法  所以在这个onCreate之前调用注入
    InjectUtils.injectPresenter(this, PRESENTER)
    PRESENTER.attachView(this)
    super.onCreate(savedInstanceState)
  }


  override fun onDestroy() {
    super.onDestroy()
    PRESENTER.detachView()
    PRESENTER.destroy()
  }

}