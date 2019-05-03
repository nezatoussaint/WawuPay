package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.hviewtech.wawupay.R

class RadioRecycleAdapter(private val mContext: Context, private val mDatas: MutableList<String>) :
  RecyclerView.Adapter<RadioRecycleAdapter.ViewHolder>() {
  private var mSelectedItem = -1

  private var mListener: (holder: String, pos: Int) -> Unit = { holder, pos -> }


  fun setOnItemClickListener(listener: (holder: String, pos: Int) -> Unit) {
    this.mListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_radio, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.radioButton.text = mDatas[position]
    holder.radioButton.isChecked = position == mSelectedItem
    holder.radioButton.tag = position
    holder.radioButton.setOnClickListener { v ->
      mSelectedItem = v.tag as Int
      notifyDataSetChanged()
      mListener.invoke(mDatas[holder.layoutPosition], holder.layoutPosition)
    }
  }

  override fun getItemCount(): Int {
    return mDatas.size
  }

  fun addData(data: String, pos: Int) {
    mDatas.add(pos, data)
    notifyItemInserted(pos)
  }

  fun removeData(pos: Int) {
    mDatas.removeAt(pos)

    notifyDataSetChanged()
    notifyItemRemoved(pos)
  }

  fun setSelection(index: Int) {
    mSelectedItem = index
    notifyDataSetChanged()
  }

  fun clearSelection() {
    mSelectedItem = -1
    notifyDataSetChanged()
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var radioButton: RadioButton

    init {
      radioButton = itemView.findViewById<View>(R.id.radio) as RadioButton
    }
  }
}
