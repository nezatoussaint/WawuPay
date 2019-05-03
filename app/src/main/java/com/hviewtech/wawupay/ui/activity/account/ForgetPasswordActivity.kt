package com.hviewtech.wawupay.ui.activity.account

import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.AppUtils
import com.hviewtech.wawupay.common.utils.VerificationUtils
import com.hviewtech.wawupay.contract.account.ForgetPasswordContract
import com.hviewtech.wawupay.data.http.observer.SimpleObserver
import com.hviewtech.wawupay.presenter.account.ForgetPasswordPresenter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.act_forget_password.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForgetPasswordActivity : BaseMvpActivity(), ForgetPasswordContract.View {
  companion object {

    val TYPE = "type"
    val LOGIN = 1
    val PAYMENT = 2
  }

  @Inject
  lateinit var mPresenter: ForgetPasswordPresenter

  private var mPasswordType = LOGIN

  override fun getLayoutId(): Int {
    return R.layout.act_forget_password
  }

  override fun initialize() {
    super.initialize()
    val intent = intent
    if (intent != null) {
      mPasswordType = intent.getIntExtra(TYPE, LOGIN)
    }

    if (mPasswordType == PAYMENT) {
      password.inputType = EditorInfo.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
      password2.setInputType(EditorInfo.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)
      val filter = InputFilter.LengthFilter(6)
      AppUtils.setEditTextInhibitInputSpace(password, filter)
      AppUtils.setEditTextInhibitInputSpace(password2, filter)

      titleView.title = "Set Payment Password"
    } else {
      titleView.title = "Recover Password"
    }
  }


  fun getCode(view: View) {
    val phone = phone.value
    if (phone.isNullOrBlank()) {
      showError("Invalid phone number")
      return
    }
    val count = 60
    Observable.interval(0, 1, TimeUnit.SECONDS)
      .take((count + 1).toLong())
      .map<Long> { time -> count - time }
      .bindToLifecycle(provider)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : SimpleObserver<Long>() {
        override fun onSubscribe(d: Disposable) {
          smsCodeTip.isEnabled = false
        }

        override fun onNext(num: Long) {
          smsCodeTip.value = getString(R.string.count_time, num)
        }

        override fun onComplete() {
          smsCodeTip.value = getString(R.string.get_code)
          smsCodeTip.isEnabled = true
        }
      })

    mPresenter.getSmsCode(phone)
  }


  override fun showVerCode(verCode: String?) {
    smsCode.value = verCode

  }

  override fun showModifyPasswordSuccess() {
    finish()
  }


  fun submit(view: View) {
    val phone = phone.value
    if (phone.isNullOrBlank()) {
      showError("Invalid phone number")
      return
    }
    val verCode = smsCode.value
    if (!VerificationUtils.checkPostcode(verCode)) {
      showError("Invalid verification code")
      return
    }

    val password = password.value
    if (TextUtils.isEmpty(password)) {
      showError("Invalid password")
      return
    }

    val password2 = password2.value
    if (password != password2) {
      showError("Password mismatch")
      return
    }
    mPresenter.modifyPassword(phone, verCode, password, mPasswordType)
  }

}