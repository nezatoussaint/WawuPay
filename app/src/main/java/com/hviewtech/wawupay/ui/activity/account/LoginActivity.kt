package com.hviewtech.wawupay.ui.activity.account

import android.text.TextUtils
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.common.action.CancelSwipeBack
import com.hviewtech.wawupay.common.action.Exit
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.account.LoginContract
import com.hviewtech.wawupay.presenter.account.LoginPresenter
import com.hviewtech.wawupay.presenter.transport.AuthPresenter
import com.hviewtech.wawupay.ui.activity.home.MainActivity
import kotlinx.android.synthetic.main.act_login.*
import javax.inject.Inject

class LoginActivity : BaseMvpActivity(), CancelSwipeBack, Exit, LoginContract.View {

  @Inject
  lateinit var mLoginPresenter: LoginPresenter

  @Inject
  lateinit var mAuthPresenter: AuthPresenter

  override fun getLayoutId(): Int {
    return R.layout.act_login
  }

  override fun initialize() {
    super.initialize()

    username.value = "18602880530"
    password.value = "123456"

    val token = HawkExt.token
    if (!token.isNullOrBlank()) {
      goActivity(MainActivity::class, true)
    }

  }

  fun login(view: View) {
    val username = username.text.toString()
    val password = password.text.toString()
    if (TextUtils.isEmpty(username)) {
      showError("Invalid phone number")
      return
    }
    if (TextUtils.isEmpty(password)) {
      showError("Invalid password")
      return
    }
    mLoginPresenter.login(username, password)

    mAuthPresenter.getAuthInfo(username, password)
  }

  fun register(view: View) {
    goActivity(RegisterActivity::class)
  }

  fun forgetPassword(view: View) {
    goActivity(ForgetPasswordActivity::class)
  }

  override fun showLoginSuccess(result: Login) {
    HawkExt.saveInfo(result)
    goActivity(MainActivity::class, true)
  }


}