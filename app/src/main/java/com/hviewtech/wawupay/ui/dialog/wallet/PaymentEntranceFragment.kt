package com.hviewtech.wawupay.ui.dialog.wallet

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.KeyBoardUtils
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.di.module.HttpModule
import com.hviewtech.wawupay.ui.dialog.BaseDialogFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.frag_payment_entrance.*

/**
 * @author su
 * @date 2018/3/17
 * @description
 */
class PaymentEntranceFragment : BaseDialogFragment() {

  //    private Function.Param<Function.Void> func;
  private var funcExtra: (String) -> Unit = {}


  override fun getAnimation(): Int {
    return R.style.AnimRightNoExit
  }

  override fun getLayoutId(): Int {
    return R.layout.frag_payment_entrance
  }

  override fun needKeyboardAutoPush(): Boolean {
    return true
  }

  override fun init() {
    initArguments()

    titleView.setOnReturnClickListener {
      dismiss()
    }

    password.requestFocus()
    RxTextView.textChanges(password)
      .subscribe { textViewBeforeTextChangeEvent ->
        if (textViewBeforeTextChangeEvent.length == 6) {
          submit.setEnabled(true)
          KeyBoardUtils.closeKeyboard(dialog)
        }
      }

    RxView.clicks(submit)
      .subscribe {
        submit()
      }
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    if (!hidden) {
      initArguments()
    }
  }

  private fun initArguments() {
    val args = arguments
    if (args != null) {
      val title = args.getString(TITLE)
      titleView.title = title
    }
  }

  fun submit() {

    val info = HawkExt.info
    val num = info?.num

    num ?: return

    val password = this.password.value ?: return

    showloading()

    HttpModule.userModel(mContext!!)
      .postValidatePayPassword(num, password)
      .bindToLifecycle(provider)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>() {
        override fun onNext(data: Any?) {
          showValidateSuccess();
        }

        override fun onError(e: Throwable) {
          super.onError(e);
          showValidateFailed();
        }
      })

  }

  fun showValidateSuccess() {

    dismissloading {
      dismiss()
      val content = password.value
      if (!content.isNullOrBlank()) {
        funcExtra.invoke(content)
      }
      password.value = null
    }

  }

  fun showValidateFailed() {
    dismissloading(true)
    password.value = null

  }

  fun title(title: String): PaymentEntranceFragment {
    val args = Bundle()
    args.putString(TITLE, title)
    arguments = args
    return this
  }

  //  fun show(manager: FragmentManager, func: () -> Unit) {
  //    super.show(manager, PaymentEntranceFragment::class.java.getName())
  //    this.funcEmpty = func
  //  }

  fun show(manager: FragmentManager, func: (String) -> Unit) {
    super.show(manager, PaymentEntranceFragment::class.java.getName())
    this.funcExtra = func
  }

  //    public void show(FragmentManager manager, Function.Param<Function.Void> func) {
  //        super.show(manager, PaymentEntranceDialog.class.getName());
  //        this.func = func;
  //    }

  //  fun show(manager: FragmentManager, func: (String, () -> Unit) -> Unit) {
  //    super.show(manager, PaymentEntranceFragment::class.java.getName())
  //    this.funcExtra = func
  //  }

  companion object {

    private val TITLE = "title"
  }
}
