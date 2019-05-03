package com.hviewtech.wawupay.ui.activity.account

import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.goIntent
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.VerificationUtils
import com.hviewtech.wawupay.contract.account.RegisterContract
import com.hviewtech.wawupay.data.http.transformer.SimpleTransformer
import com.hviewtech.wawupay.presenter.account.RegisterPresenter
import com.hviewtech.wawupay.ui.activity.common.WebActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.act_register.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterActivity : BaseMvpActivity(), RegisterContract.View {

  @Inject
  lateinit var mRegisterPresenter: RegisterPresenter

  override fun getLayoutId(): Int {
    return R.layout.act_register
  }

  override fun initialize() {
    super.initialize()
    val content = getString(R.string.prompt_register_wowpay_protocol)
    val subcontent = getString(R.string.prompt_wowpay_protocol)
    val spanText = SpannableString(content)
    spanText.setSpan(object : ClickableSpan() {

      override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = resources.getColor(R.color.text_label)
        ds.isUnderlineText = true      //设置下划线
      }

      override fun onClick(view: View) {
        val intent = Intent(mContext, WebActivity::class.java)
        intent.putExtra(WebActivity.URL, Constants.URL.USER_AGREEMENT)
        goIntent(intent)

      }
    }, content.length - subcontent.length, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


    protocol.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明，否则会一直出现高亮
    protocol.text = spanText
    protocol.movementMethod = LinkMovementMethod.getInstance()
  }


  fun getCode(view: View) {
    val phone = phone.value
    if (phone.isNullOrBlank()) {
      showError("Invalid phone number")
      return
    }
    val count = 60
    Observable.interval(1, TimeUnit.SECONDS)
      .bindToLifecycle(provider)
      .compose(SimpleTransformer())
      .take((count + 1).toLong())
      .map<Long> { time -> (count - time) }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : Observer<Long> {
        override fun onError(e: Throwable) {
        }

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

    mRegisterPresenter.getSmsCode(phone)
  }

  fun register(view: View) {
    val username = username.value
    if (username.isNullOrBlank()) {
      showError("Invalid username")
      return
    }
    val firstName = firstname.value
    if (firstName.isNullOrBlank()) {
      showError("Invalid firstName")
      return
    }
    val lastName = lastname.value
    if (lastName.isNullOrBlank()) {
      showError("Invalid lastName")
      return
    }
    val email = email.value
    if (!VerificationUtils.matcherEmail(email)) {
      showError("Invalid email")
      return
    }
    val phone = phone.value
    if (phone.isNullOrBlank()) {
      showError("Invalid phone")
      return
    }
    val smsCode = smsCode.value
    if (smsCode.isNullOrBlank()) {
      showError("Invalid verification code")
      return
    }
    val password = password.value
    if (password.isNullOrBlank()) {
      showError("Invalid assword")
      return
    }
    val password2 = password2.value
    if (password != password2) {
      showError("Password mismatch")
      return
    }

    val agreeProtocol = agreeProtocol.isChecked
    if (!agreeProtocol) {
      showError("User agreement unchecked")
      return
    }
    mRegisterPresenter.verficatePhone(phone, smsCode)
  }


  override fun showVerCode(verCode: String?) {
    smsCode.value = verCode
  }

  override fun showVericatePhoneSuccess() {
    val nickname = username.value
    val firstName = firstname.value
    val lastName = lastname.value
    val email = email.value
    val phone = phone.value
    val loginPassword = password.value
    mRegisterPresenter.register(nickname, firstName, lastName, email, phone, loginPassword)
  }

  override fun showRegisterSuccess(result: Login?) {

    HawkExt.saveInfo(result)

    goActivity(IdentityProfileActivity::class, true)
  }

}