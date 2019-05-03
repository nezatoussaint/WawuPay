package com.hviewtech.wawupay.ui.adapter.wallet

import android.content.Context
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter

/**
 * Created by su on 2018/4/12.
 */

class BankCardAddAdapter(context: Context, private val mView: IView?) : BaseAdapter<Any>(context) {

  interface IView {

    fun addBankCard()
  }

  override fun getLayoutId(): Int {
    return R.layout.item_bankcard_add
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = SingleLayoutHelper()
    val size = mContext.resources.getDimensionPixelSize(R.dimen.dp_44)
    helper.paddingTop = size
    return helper
  }

  override fun getItemCount(): Int {
    return 1
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    holder.itemView.setOnClickListener {
      mView?.addBankCard()
    }
  }

}
