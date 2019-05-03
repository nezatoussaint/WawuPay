package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.common.decoration.DividerItemDecoration
import com.hviewtech.wawupay.ui.adapter.base.BaseOriginAdapter

abstract class BaseDropDownView<T : Any> : LinearLayout {

  protected var textView: TextView? = null
  private var popupWindow: PopupWindow? = null
  protected var dataList: List<T> = ArrayList<T>()
  private var inflater: LayoutInflater
  protected var mContext: Context
  var onItemClicked: (index: Int, data: T) -> Unit = { index, data -> }
    set(value) {
      field = value
    }

  var onEmptyClicked: () -> Unit = {}
    set(value) {
      field = value
    }

  /**
   * 获取当前data
   */
  var curData: T? = null
    get() = field
    private set(value) {
      field = value
    }

  /**
   * 设置当前data
   */
  var data: T?
    get() = curData
    set(value) {
      this.curData = value
      initText(textView!!, value)
    }

  var itemsData: List<T>? = arrayListOf()
    set(value) {
      dataList = value ?: arrayListOf()
      if (dataList.size > 0) {
        val item = dataList[0]
        initText(textView!!, item, true)
        this.curData = item
      } else {
        textView!!.text = ""
        this.curData = null
      }
    }

  constructor(context: Context) : super(context) {
    mContext = context
    inflater = LayoutInflater.from(context)
    initView()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    mContext = context
    inflater = LayoutInflater.from(context)
    initView()
  }

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    mContext = context
    inflater = LayoutInflater.from(context)
    initView()
  }

  fun initView() {
    inflater!!.inflate(layoutId, this, true)
    textView = findViewById(textId)
    setOnClickListener { v ->
      itemClicked()
    }
  }

  private fun itemClicked() {
    if (popupWindow == null) {
      showPopWindow()
    } else {
      closePopWindow()
    }
  }


  /**
   * 打开下拉列表弹窗
   */
  private fun showPopWindow() {
    // 加载popupWindow的布局文件

    if (dataList.size == 0) {
      onEmptyClicked.invoke()
      return
    }

    val contentView = inflater!!.inflate(itemRootId, null, false)
    val recyclerView = contentView.findViewById<RecyclerView>(R.id.recyclerView)
    val adapter = DropDownListAdapter(mContext)
    adapter.setDatas(dataList)
    recyclerView.layoutManager = LinearLayoutManager(mContext)
    recyclerView.adapter = adapter
    val decoration = DividerItemDecoration(mContext)
    initDivider(decoration)
    recyclerView.addItemDecoration(decoration)
    popupWindow = PopupWindow(contentView, width + paddingLeft + paddingRight, LinearLayout.LayoutParams.WRAP_CONTENT)
    popupWindow!!.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
    popupWindow!!.isOutsideTouchable = true
    popupWindow!!.showAsDropDown(this)
  }

  protected open fun initDivider(decoration: DividerItemDecoration) {
    decoration.setColor(0xFFE5E5E5)
  }

  /**
   * 关闭下拉列表弹窗
   */
  private fun closePopWindow() {
    if (popupWindow != null) {
      popupWindow!!.dismiss()
      popupWindow = null
    }
  }

  /**
   * 设置数据
   *
   * @param list
   */
  //  fun setItemsData(list: ArrayList<String>) {
  //    dataList = list
  //    textView!!.text = list[0]
  //  }


  /**
   * 数据适配器
   *
   * @author caizhiming
   */
  internal inner class DropDownListAdapter(val context: Context) : BaseOriginAdapter<T>(context) {
    override fun getLayoutId(): Int = itemLayoutId


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val tvContent = holder.getView<TextView>(R.id.content)
      // 设置数据
      val item = getItem(position)
      initText(tvContent!!, item)

      holder.itemView.setOnClickListener {
        this@BaseDropDownView.data = item
        onItemClicked.invoke(position, item)
        closePopWindow()
      }

    }
  }

  abstract fun initText(textView: TextView, item: T?, init: Boolean = false)

  abstract val textId: Int

  abstract val layoutId: Int

  abstract val itemRootId: Int

  abstract val itemLayoutId: Int

}
