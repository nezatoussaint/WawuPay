package com.hviewtech.wawupay.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.hviewtech.wawupay.R

/**
 * Created by liuqiang on 10/23/15.
 */
class AlertDialog(private val context: Context) {
  private var dialog: Dialog? = null
  private var lLayout_bg: LinearLayout? = null
  private var txt_title: TextView? = null
  private var txt_msg: TextView? = null
  private var btn_neg: Button? = null
  private var btn_pos: Button? = null
  private var img_line: ImageView? = null
  private val display: Display
  private var showTitle = false
  private var showMsg = false
  private var showPosBtn = false
  private var showNegBtn = false

  init {
    val windowManager = context
      .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    display = windowManager.defaultDisplay
  }

  fun builder(): AlertDialog {
    // 获取Dialog布局
    val view = LayoutInflater.from(context).inflate(
      R.layout.iosdialog_alertdialog, null
    )

    // 获取自定义Dialog布局中的控件
    lLayout_bg = view.findViewById<View>(R.id.lLayout_bg) as LinearLayout
    txt_title = view.findViewById<View>(R.id.txt_title) as TextView
    txt_title!!.visibility = View.GONE
    txt_msg = view.findViewById<View>(R.id.txt_msg) as TextView
    txt_msg!!.visibility = View.GONE
    btn_neg = view.findViewById<View>(R.id.btn_neg) as Button
    btn_neg!!.visibility = View.GONE
    btn_pos = view.findViewById<View>(R.id.btn_pos) as Button
    btn_pos!!.visibility = View.GONE
    img_line = view.findViewById<View>(R.id.img_line) as ImageView
    img_line!!.visibility = View.GONE

    // 定义Dialog布局和参数
    dialog = Dialog(context, R.style.AlertDialogStyle)
    dialog!!.setContentView(view)

    // 调整dialog背景大小
    lLayout_bg!!.layoutParams = FrameLayout.LayoutParams(
      (display
        .width * 0.8827).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT
    )

    return this
  }

  fun setTitle(title: String): AlertDialog {
    showTitle = true
    if ("" == title) {
      txt_title!!.text = ""
    } else {
      txt_title!!.text = title
    }
    return this
  }

  fun setMsg(msg: String): AlertDialog {
    showMsg = true
    if ("" == msg) {
      txt_msg!!.text = ""
    } else {
      txt_msg!!.text = msg
    }
    return this
  }

  fun setMsg(msg: String, color: Int): AlertDialog {
    showMsg = true
    if ("" == msg) {
      txt_msg!!.text = ""
    } else {
      txt_msg!!.text = msg
    }
    if (color != 0) {
      txt_msg!!.setTextColor(color)
    }
    return this
  }


  fun setCancelable(cancel: Boolean): AlertDialog {
    dialog!!.setCancelable(cancel)
    return this
  }

  fun setCanceledOnTouchOutside(cancel: Boolean): AlertDialog {
    dialog!!.setCanceledOnTouchOutside(cancel)
    return this
  }

  fun setPositiveButton(
    text: String,
    listener: (View)->Unit={}
  ): AlertDialog {
    showPosBtn = true
    if ("" == text) {
      btn_pos!!.text = "Yes"
    } else {
      btn_pos!!.text = text
    }
    btn_pos!!.setOnClickListener { v ->
      dialog!!.dismiss()
      listener.invoke(v)
    }
    return this
  }

  fun setNegativeButton(
    text: String,
    listener: (View)->Unit={}
  ): AlertDialog {
    showNegBtn = true
    if ("" == text) {
      btn_neg!!.text = "No"
    } else {
      btn_neg!!.text = text
    }
    btn_neg!!.setOnClickListener { v ->
      dialog!!.dismiss()
      listener.invoke(v)
    }
    return this
  }

  private fun setLayout() {
    if (!showTitle && !showMsg) {
      txt_title!!.text = ""
      txt_title!!.visibility = View.VISIBLE
    }

    if (showTitle) {
      txt_title!!.visibility = View.VISIBLE
    }

    if (showMsg) {
      txt_msg!!.visibility = View.VISIBLE
    }

    if (!showPosBtn && !showNegBtn) {
      btn_pos!!.text = "Yes"
      btn_pos!!.visibility = View.VISIBLE
      btn_pos!!.setBackgroundResource(R.drawable.bg_alertbutton_none)
      btn_pos!!.setOnClickListener { dialog!!.dismiss() }
    }

    if (showPosBtn && showNegBtn) {
      btn_pos!!.visibility = View.VISIBLE
      btn_pos!!.setBackgroundResource(R.drawable.bg_alertbutton_left)
      btn_neg!!.visibility = View.VISIBLE
      btn_neg!!.setBackgroundResource(R.drawable.bg_alertbutton_right)
      img_line!!.visibility = View.VISIBLE
    }

    if (showPosBtn && !showNegBtn) {
      btn_pos!!.visibility = View.VISIBLE
      btn_pos!!.setBackgroundResource(R.drawable.bg_alertbutton_bottom)
    }

    if (!showPosBtn && showNegBtn) {
      btn_neg!!.visibility = View.VISIBLE
      btn_neg!!.setBackgroundResource(R.drawable.bg_alertbutton_bottom)
    }
  }

  fun show() {
    setLayout()
    dialog!!.show()
  }
}
