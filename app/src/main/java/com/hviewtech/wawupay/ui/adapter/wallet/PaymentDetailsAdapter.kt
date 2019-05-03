package com.hviewtech.wawupay.ui.adapter.wallet

import android.content.Context
import android.widget.TextView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.DateUtils
import com.hviewtech.wawupay.common.utils.NumberUtils
import com.hviewtech.wawupay.ui.adapter.base.BaseAdapter

class PaymentDetailsAdapter(context: Context, private val mView: IView?) : BaseAdapter<PaymentDetail>(context) {


  interface IView {
    fun onClickDetail(detail: PaymentDetail)
  }

  override fun getLayoutId(): Int {
    return R.layout.item_payment_details
  }

  override fun onCreateLayoutHelper(): LayoutHelper {
    val helper = LinearLayoutHelper()
    val size = mContext.resources.getDimensionPixelSize(R.dimen.dp_0_5)
    val margin = mContext.resources.getDimensionPixelSize(R.dimen.dp_16)
    helper.setDividerHeight(size)
    helper.marginTop = margin
    helper.marginBottom = margin
    return helper
  }

  override fun onBindViewHolder(holder: BaseAdapter.ViewHolder, position: Int) {
    val item = mDatas[position]

    holder.getView<TextView>(R.id.title)?.value = item.remark
    holder.getView<TextView>(R.id.time)?.value = DateUtils.formatDataTime(item.createTime)
    holder.getView<TextView>(R.id.account)?.value = (if (item.type == 1) "+" else "-") +
        NumberUtils.toPlainString(item.amount) + "RWF"

    holder.itemView.setOnClickListener {
      mView?.onClickDetail(item)
    }
  }

}
