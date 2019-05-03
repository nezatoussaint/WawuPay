package com.hviewtech.wawupay.contract.wallet

import android.graphics.Bitmap
import com.hviewtech.wawupay.bean.remote.wallet.PrepayId
import com.hviewtech.wawupay.contract.Contract

/**
 * @author su
 * @date 2018/3/17
 * @description
 */

interface PaymentContract {

  interface View : Contract.View {

    fun updateQRCode(bitmap: Bitmap)

    fun updateQRCode(result: PrepayId?)
  }

  interface Presenter : Contract.Presenter
}
