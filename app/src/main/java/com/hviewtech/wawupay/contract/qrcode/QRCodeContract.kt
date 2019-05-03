package com.hviewtech.wawupay.contract.qrcode


import com.hviewtech.wawupay.bean.remote.wallet.PayInfo
import com.hviewtech.wawupay.contract.Contract

/**
 * @author Eric
 * @version 1.0
 * @description
 */

interface QRCodeContract {
  interface View : Contract.View {


    fun showPaymentInfo(payInfo: PayInfo? = null)

  }

  interface Presenter : Contract.Presenter
}
