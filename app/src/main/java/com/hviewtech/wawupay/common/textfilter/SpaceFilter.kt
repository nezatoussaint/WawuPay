package com.hviewtech.wawupay.common.textfilter

import android.text.InputFilter
import android.text.Spanned

/**
 * 设置银行卡格式
 */
class SpaceFilter(
    /**
     * 输入框每多少位空格
     */
    private val limitLength: Int
) : InputFilter {

    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence? {
        if ("" == source.toString()) {
            return null
        }
        val dValue = dest.toString()
        val splitArray = dValue.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (splitArray.size > 1) {
            val dotValue = splitArray[1]
            val diff = dotValue.length + 1 - limitLength
            if (diff > 0) {
                return source.subSequence(start, end - diff)
            }
        }
        return null
    }
}