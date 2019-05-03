package com.hviewtech.wawupay.base

import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/11/23
 * @description 用于管理多个presenter
 */
class MultiPresenter : Contract.Presenter {

  private val mPresenters = ArrayList<Contract.Presenter>()

  fun bind(vararg presenters: Contract.Presenter) {
    mPresenters.addAll(presenters)
  }

  override fun attachView(view: Contract.View) {
    mPresenters.forEach { presenter ->
      presenter.attachView(view)
    }
  }

  override fun detachView() {
    mPresenters.forEach { presenter ->
      presenter.detachView()
    }
  }

  override fun destroy() {
    mPresenters.clear()
  }
}