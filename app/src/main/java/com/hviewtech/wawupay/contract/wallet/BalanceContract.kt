package com.hviewtech.wawupay.contract.wallet


import com.hviewtech.wawupay.bean.remote.wallet.BalanceInfo
import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/18
 * @description
 */

interface BalanceContract {
  interface View : Contract.View {

    fun showBalanceInfo(result: BalanceInfo?)
  }

  interface Presenter : Contract.Presenter
}
