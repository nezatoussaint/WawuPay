package com.hviewtech.wawupay.ui.activity.profile

import android.content.Intent
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.ui.activity.account.ForgetPasswordActivity

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class PasswordSettingActivity : BaseActivity() {
  override fun getLayoutId(): Int {
    return R.layout.act_password_setting
  }


  fun settingPaymentPassword(view: View) {
    val intent = Intent(mContext, ForgetPasswordActivity::class.java)
    intent.putExtra(ForgetPasswordActivity.TYPE, ForgetPasswordActivity.PAYMENT)
    goIntent(intent)
  }

  fun settingLoginPassword(view: View) {
    val intent = Intent(mContext, ForgetPasswordActivity::class.java)
    intent.putExtra(ForgetPasswordActivity.TYPE, ForgetPasswordActivity.LOGIN)
    goIntent(intent)
  }
}
