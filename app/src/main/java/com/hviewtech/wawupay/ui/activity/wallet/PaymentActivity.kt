package com.hviewtech.wawupay.ui.activity.wallet

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.PrepayId
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.contract.wallet.PaymentContract
import com.hviewtech.wawupay.presenter.wallet.PaymentPresenter
import kotlinx.android.synthetic.main.act_payment.*
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/17
 * @description
 */

class PaymentActivity : BaseMvpActivity(), PaymentContract.View {


  companion object {

    private val RC_SET_AMOUNT = 1
  }

  @Inject
  lateinit var mPresenter: PaymentPresenter

  private var mQrcodeSize: Int = 0

  override fun getLayoutId(): Int {
    return R.layout.act_payment
  }

  override fun initialize() {
    super.initialize()
    mQrcodeSize = resources.getDimensionPixelSize(R.dimen.dp_158)

    mPresenter.generatePrepayUrl(false, BigDecimal.ZERO)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == RC_SET_AMOUNT) {
      val amount = data?.getSerializableExtra(SetAmountActivity.AMOUNT) as? BigDecimal
      if (amount != null) {
        mPresenter.generatePrepayUrl(amount = amount)
      }
    }
  }

  fun setAmount(view: View) {
    goActivity(SetAmountActivity::class, RC_SET_AMOUNT)
  }

  override fun updateQRCode(bitmap: Bitmap) {
    qrcode.setImageBitmap(bitmap)
  }

  override fun updateQRCode(result: PrepayId?) {
    mPresenter.generateQRCode(result?.payUrl, mQrcodeSize)
  }

}
