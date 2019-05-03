package com.hviewtech.wawupay.ui.activity.profile

import android.content.Intent
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.ui.activity.common.WebActivity

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class SettingsActivity : BaseActivity() {
  override fun getLayoutId(): Int {
    return R.layout.act_settings
  }

  override fun initialize() {
    super.initialize()
  }

  fun logout(view: View) {
    ActivityUtils.toReLogin(mContext)
  }

  fun settingPassword(view: View) {
    goActivity(PasswordSettingActivity::class)
  }

  fun showPrivacy(view: View) {
    val intent = Intent(mContext, WebActivity::class.java)
    intent.putExtra(WebActivity.URL, Constants.URL.PRIVACY)
    goIntent(intent)
  }

  fun showAboutUs(view: View) {
    val intent = Intent(mContext, WebActivity::class.java)
    intent.putExtra(WebActivity.URL, Constants.URL.ABOUT_US)
    goIntent(intent)
  }

  fun showHelp(view: View) {
    val intent = Intent(mContext, WebActivity::class.java)
    intent.putExtra(WebActivity.URL, Constants.URL.HELP)
    goIntent(intent)
  }
}
