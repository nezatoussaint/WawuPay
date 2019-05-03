package com.hviewtech.wawupay.ui.dialog.home

import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import android.view.View
import com.hviewtech.wawupay.R
import me.shaohui.bottomdialog.BaseBottomDialog

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class HomeBottomDialog : BaseBottomDialog() {

  private var mActionListener: ActionListener? = null

  interface ActionListener {
    fun onAction(isShow: Boolean)

    fun scanQRCode()

    fun goPersonalWallet()

    fun showPersonalQRCode()
  }

  override fun getLayoutRes(): Int {
    return R.layout.dialog_home_bottom
  }

  override fun onStart() {
    super.onStart()
    val window = dialog.window ?: return
    val params = window.attributes
    params.width = (window.windowManager.defaultDisplay.width * 0.8826f).toInt()
    window.attributes = params
  }

  override fun bindView(v: View) {
    val ivClose = v.findViewById<View>(R.id.iv_close)
    val vScan = v.findViewById<View>(R.id.ll_scan)
    val vWallet = v.findViewById<View>(R.id.ll_wallet)
    val vQrcode = v.findViewById<View>(R.id.ll_qrcode)
    ivClose.setOnClickListener { v1 -> dismiss() }
    vScan.setOnClickListener { v1 ->
      mActionListener!!.scanQRCode()
      dismiss()
    }
    vWallet.setOnClickListener { v1 ->
      mActionListener!!.goPersonalWallet()

      dismiss()
    }
    vQrcode.setOnClickListener { v1 ->
      mActionListener!!.showPersonalQRCode()
      dismiss()
    }
  }


  override fun getDimAmount(): Float {
    return 0.3f
  }

  override fun getFragmentTag(): String {
    return javaClass.name
  }

  fun setActionListener(actionListener: ActionListener): HomeBottomDialog {
    mActionListener = actionListener
    return this
  }

  override fun show(fragmentManager: FragmentManager) {
    super.show(fragmentManager)
    if (mActionListener != null) {
      mActionListener!!.onAction(true)
    }
  }

  override fun onCancel(dialog: DialogInterface?) {
    if (mActionListener != null) {
      mActionListener!!.onAction(false)
    }
    super.onCancel(dialog)
  }

  override fun onDismiss(dialog: DialogInterface?) {
    if (mActionListener != null) {
      mActionListener!!.onAction(false)
    }
    super.onDismiss(dialog)
  }

  companion object {

    val fragTag: String
      get() = HomeBottomDialog::class.java.name
  }


}
