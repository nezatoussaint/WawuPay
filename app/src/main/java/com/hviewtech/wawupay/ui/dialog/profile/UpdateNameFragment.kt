package com.hviewtech.wawupay.ui.dialog.profile

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.dialog.CommonDialogFragment
import kotlinx.android.synthetic.main.frag_update_name.*

/**
 * @author Eric
 * @version 1.0
 * @description
 */
class UpdateNameFragment : CommonDialogFragment() {
  private var func: (String?) -> Unit = {}


  override fun getLayoutId(): Int {
    return R.layout.frag_update_name
  }

  override fun getAnimation(): Int {
    return R.style.AnimRight
  }


  override fun needKeyboardAutoPush(): Boolean {
    return true
  }

  override fun init() {
    initArguments()
    text.requestFocus()

    titleView.setOnMoreClickListener {
      val text = this.text.value
      this.text.value = ""
      func.invoke(text)
      dismiss()

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
      this.titleView.title = title
      this.title.value = title
    }
  }

  fun title(title: String): UpdateNameFragment {
    val args = Bundle()
    args.putString(TITLE, title)
    arguments = args
    return this
  }

  fun show(manager: FragmentManager, func: (String?) -> Unit = {}) {
    super.show(manager, UpdateNameFragment::class.java.getName())
    this.func = func
  }

  companion object {
    val TITLE = "layout_title"
  }
}
