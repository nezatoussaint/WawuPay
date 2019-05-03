package com.hviewtech.wawupay.ui.fragment.wallet

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.afollestad.materialdialogs.MaterialDialog
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.di.module.HttpModule
import com.hviewtech.wawupay.ui.dialog.BaseDialogFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.frag_addbank.*
import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */
class AddBankFragment @Inject
constructor() : BaseDialogFragment() {


  companion object {
    val TITLE = "layout_title"
  }


  private var func: () -> Unit = {}
  private var whichBankCard: Int = 0


  override fun getLayoutId(): Int {
    return R.layout.frag_addbank
  }

  override fun getAnimation(): Int {
    return R.style.AnimRight
  }

  override fun init() {
    initArguments()


    bankList.setOnClickListener {
      showBankList()
    }


    next.setOnClickListener { goNext() }
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

  fun goNext() {
    val info = HawkExt.info
    info ?: return


    val bankName = bankcard.value
    val cardNum = cardNum.textWithoutSpace
    val realName = HawkExt.info?.realName


    val userType = 1
    HttpModule.apiModel(mContext!!)
      .postAddBankCard(userType, info.num, realName, bankName, cardNum)
      .bindToLifecycle(provider)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(this) {
        override fun onNext(data: Any?) {
          showBankCardAdded()
        }
      })

  }

  fun showBankCardAdded() {
    dismiss()
    func.invoke()
  }

  fun showBankList() {
    val items = resources.getStringArray(R.array.bank_category)
    MaterialDialog.Builder(mContext!!)
      .items(*items)
      .autoDismiss(true)
      .itemsCallbackSingleChoice(
        whichBankCard
      ) { dialog, view, which, text ->
        whichBankCard = which
        bankcard.value = items[which]
        true // allow selection
      }.show()
  }

  fun title(title: String): AddBankFragment {
    val args = Bundle()
    args.putString(TITLE, title)
    arguments = args
    return this
  }

  fun show(manager: FragmentManager?, func: () -> Unit = {}) {
    super.show(manager, AddBankFragment::class.java.getName())
    this.func = func
  }


}
