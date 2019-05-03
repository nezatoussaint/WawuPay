package com.hviewtech.wawupay.contract.profile


import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/25
 * @description
 */

interface ReportContract {
  interface View : Contract.View {

    fun showReportSuccess()
  }

  interface Presenter : Contract.Presenter
}
