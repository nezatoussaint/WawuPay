package com.hviewtech.wawupay.ui.adapter.wallet

import android.content.Context
import android.widget.TextView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter

/**
 * Created by su on 2018/4/11.
 */

class BankCardAdapter(mContext: Context, private val mView: IView?) : BaseAdapter<BankCard>(mContext) {


  interface IView {
    fun onClickManageBankCard(card: BankCard?)
  }

  override fun getLayoutId(): Int {
    return R.layout.item_bankcard
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    val size = mContext.resources.getDimensionPixelSize(R.dimen.dp_16)
    helper.marginBottom = size
    return helper

  }

  override fun onBindViewHolder(holder: BaseAdapter.ViewHolder, position: Int) {
    val item = getItem(position)

    holder.getView<TextView>(R.id.bankName)?.value = item.bankName
    holder.getView<TextView>(R.id.realName)?.value = item.realName
    holder.getView<TextView>(R.id.cardNum)?.value = item.cardNo
    holder.itemView.setOnClickListener {
      mView?.onClickManageBankCard(item)
    }
  }


}
