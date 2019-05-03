package com.hviewtech.wawupay.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hviewtech.wawupay.common.utils.InjectUtils
import com.hviewtech.wawupay.contract.Contract
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class BaseMvpFragment : BaseFragment(), Contract.View, HasSupportFragmentInjector {

  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

  var PRESENTER: MultiPresenter = MultiPresenter()

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return childFragmentInjector
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return LayoutInflater.from(container?.context).inflate(getLayoutId(), container, false)
  }

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    //bindPresenter(PRESENTER)
    InjectUtils.inject(this, view)
    PRESENTER.attachView(this)
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    PRESENTER.detachView()
  }

  override fun onDestroy() {
    super.onDestroy()
    PRESENTER.destroy()
  }


}