package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.hviewtech.wawupay.R

/**
 * Created by hzlinxuanxuan on 2015/12/22.
 * 该控件是支持输入时自带控件的，目前支持xml属性指定，也支持代码指定该输入卡所属的类型
 * 现在支持：电话、卡号、身份证号，或者无类型（正常输入）
 */
class ContentWithSpaceEditText : android.support.v7.widget.AppCompatEditText {

  private var contentType: Int = 0

  private val watcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      if (s == null) {
        return
      }
      //判断是否是在中间输入，需要重新计算
      val isMiddle = start + count < s.length
      //在末尾输入时，是否需要加入空格
      var isNeedSpace = false
      if (!isMiddle && isSpace(s.length)) {
        isNeedSpace = true
      }
      if (isMiddle || isNeedSpace) {
        var newStr = s.toString()
        newStr = newStr.replace(" ", "")
        val sb = StringBuilder()
        var spaceCount = 0
        for (i in 0 until newStr.length) {
          sb.append(newStr.substring(i, i + 1))
          //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
          if (isSpace(i + 2 + spaceCount)) {
            sb.append(" ")
            spaceCount += 1
          }
        }
        removeTextChangedListener(this)
        setText(sb)
        //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
        if (!isMiddle || count > 1) {
          setSelection(sb.length)
        } else if (isMiddle) {
          //如果是删除
          if (count == 0) {
            //如果删除时，光标停留在空格的前面，光标则要往前移一位
            if (isSpace(start - before + 1)) {
              setSelection(if (start - before > 0) start - before else 0)
            } else {
              setSelection(if (start - before + 1 > sb.length) sb.length else start - before + 1)
            }
          } else {
            if (isSpace(start - before + count)) {
              setSelection(if (start + count - before + 1 < sb.length) start + count - before + 1 else sb.length)
            } else {
              setSelection(start + count - before)
            }
          } //如果是增加
        }
        addTextChangedListener(this)
      }
    }

    override fun afterTextChanged(s: Editable) {}
  }

  val textWithoutSpace: String
    get() = super.getText().toString().replace(" ", "")

  @JvmOverloads
  constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
    parseAttributeSet(context, attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    parseAttributeSet(context, attrs)
  }

  private fun parseAttributeSet(context: Context, attrs: AttributeSet?) {
    val ta = context.obtainStyledAttributes(attrs, R.styleable.ContentWithSpaceEditText, 0, 0)
    contentType = ta.getInt(R.styleable.ContentWithSpaceEditText_epaysdk_type, 0)
    ta.recycle()
    initType()
    setSingleLine()
    addTextChangedListener(watcher)
  }

  private fun initType() {
    if (contentType == TYPE_PHONE) {
      inputType = InputType.TYPE_CLASS_NUMBER
      filters = arrayOf<InputFilter>(InputFilter.LengthFilter(13))
    } else if (contentType == TYPE_CARD) {
      inputType = InputType.TYPE_CLASS_NUMBER
      filters = arrayOf<InputFilter>(InputFilter.LengthFilter(31))
    } else if (contentType == TYPE_IDCARD) {
      filters = arrayOf<InputFilter>(InputFilter.LengthFilter(21))
    }
  }

  fun setContentType(contentType: Int) {
    this.contentType = contentType
    initType()
  }

  fun checkTextRight(): Boolean {
    //        String text = getTextWithoutSpace();
    //        //这里做个简单的内容判断
    //        if (contentType == TYPE_PHONE) {
    //            if (TextUtils.isEmpty(text)) {
    //                ToastUtils.show("手机号不能为空，请输入正确的手机号");
    //            } else if (text.length() < 11) {
    //                ToastUtils.show("手机号不足11位，请输入正确的手机号");
    //            } else {
    //                return true;
    //            }
    //        } else if (contentType == TYPE_CARD) {
    //            if (TextUtils.isEmpty(text)) {
    //                ToastUtils.show("银行卡号不能为空，请输入正确的银行卡号");
    //            } else if (text.length() < 14) {
    //                ToastUtils.show("银行卡号位数不正确，请输入正确的银行卡号");
    //            } else {
    //                return true;
    //            }
    //        } else if (contentType == TYPE_IDCARD) {
    //            if (TextUtils.isEmpty(text)) {
    //                ToastUtils.show("身份证号不能为空，请输入正确的身份证号");
    //            } else if (text.length() < 18) {
    //                ToastUtils.show("身份证号不正确，请输入正确的身份证号");
    //            } else {
    //                return true;
    //            }
    //        }
    return false
  }

  private fun isSpace(length: Int): Boolean {
    if (contentType == TYPE_PHONE) {
      return isSpacePhone(length)
    } else if (contentType == TYPE_CARD) {
      return isSpaceCard(length)
    } else if (contentType == TYPE_IDCARD) {
      return isSpaceIDCard(length)
    }
    return false
  }

  private fun isSpacePhone(length: Int): Boolean {
    return if (length < 4) {
      false
    } else if (length == 4) {
      true
    } else (length + 1) % 5 == 0
  }

  private fun isSpaceCard(length: Int): Boolean {
    return length % 5 == 0
  }

  private fun isSpaceIDCard(length: Int): Boolean {
    return if (length <= 6) {
      false
    } else if (length == 7) {
      true
    } else (length - 2) % 5 == 0
  }

  companion object {
    val TYPE_PHONE = 0
    val TYPE_CARD = 1
    val TYPE_IDCARD = 2
  }

} 