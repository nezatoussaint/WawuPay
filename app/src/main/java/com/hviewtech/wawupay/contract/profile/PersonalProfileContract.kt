package com.hviewtech.wawupay.contract.profile


import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @version 1.0
 * @description
 */

interface PersonalProfileContract {
  interface View : Contract.View {

    fun updateProvinceCities(showProgress: Boolean, result: ProvinceCitys?)
  }

  interface Presenter : Contract.Presenter
}
