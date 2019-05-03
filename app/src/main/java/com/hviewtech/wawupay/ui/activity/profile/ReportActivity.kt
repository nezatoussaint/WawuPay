package com.hviewtech.wawupay.ui.activity.profile

import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.contract.profile.ReportContract
import com.hviewtech.wawupay.presenter.profile.ReportPresenter
import kotlinx.android.synthetic.main.act_report.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/25
 * @description
 */

class ReportActivity : BaseMvpActivity(), ReportContract.View {

  companion object {

    val DETAIL = "detail"
  }

  @Inject
  lateinit var mPresenter: ReportPresenter

  private var detail: PaymentDetail? = null

  override fun getLayoutId(): Int {
    return R.layout.act_report
  }

  override fun initialize() {
    super.initialize()
    val intent = intent
    if (intent != null) {
      detail = intent.getSerializableExtra(DETAIL) as PaymentDetail

      if (detail == null) {
        finish()
        return
      }


      orderNo.value = "Order No:${detail?.orderNo}"


    }
  }

  fun report(view: View) {
    if (detail == null) {
      return
    }

    val title = etTitle.value
    val content = etContent.value

    if (title.isNullOrBlank()) {
      showError("Input title")
      return
    }
    if (content.isNullOrBlank()) {
      showError("Input content")
      return
    }

    mPresenter.complaintInfo(title, content, detail?.orderNo)
  }

  override fun showReportSuccess() {
    toast("Report successful")
    finish()
  }

}
