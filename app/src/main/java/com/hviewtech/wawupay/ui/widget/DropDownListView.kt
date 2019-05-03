package com.hviewtech.wawupay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.remote.transport.Site

@Deprecated("Extends BaseDropDownListView")
class DropDownListView : LinearLayout {

  private var textView: TextView? = null
  private var popupWindow: PopupWindow? = null
  private var dataList: List<Site> = ArrayList<Site>()
  private var inflater: LayoutInflater? = null
  private var mContext: Context? = null
  var onItemClicked: (Site) -> Unit = {}
    set(value) {
      field = value
    }
  var onEmptyClicked: () -> Unit = {}
    set(value) {
      field = value
    }

  var site: Site? = null
    get() = field

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
    inflater!!.inflate(R.layout.view_dropdownlist_search, this, true)
    textView = findViewById(R.id.text)
    setOnClickListener { v ->
      if (popupWindow == null) {
        showPopWindow()
      } else {
        closePopWindow()
      }
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

    val contentView = inflater!!.inflate(R.layout.view_dropdownlist_searchroot, null, false)
    val listView = contentView.findViewById<ListView>(R.id.listView)
    listView.adapter = DropDownListAdapter(dataList)
    popupWindow = PopupWindow(contentView, width + paddingLeft + paddingRight, LinearLayout.LayoutParams.WRAP_CONTENT)
    popupWindow!!.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
    popupWindow!!.isOutsideTouchable = true
    popupWindow!!.showAsDropDown(this)
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

  var itemsData: List<Site>? = arrayListOf()
    set(value) {
      dataList = value ?: arrayListOf()
      if (dataList.size > 0) {
        val site = dataList[0]
        textView!!.text = site.siteName
        this.site = site
      } else {
        textView!!.text = ""
        this.site = null
      }
    }


  /**
   * 数据适配器
   *
   * @author caizhiming
   */
  internal inner class DropDownListAdapter(var mData: List<Site>) : BaseAdapter() {

    override fun getCount(): Int {
      return mData.size
    }

    override fun getItem(position: Int): Site {
      return mData[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      var view = convertView
      // 自定义视图
      var listItemView: ListItemView? = null
      if (view == null) {
        // 获取list_item布局文件的视图
        view = inflater!!.inflate(R.layout.view_dropdownlist_searchitem, null)
        listItemView = ListItemView()
        listItemView.content = view!!.findViewById(R.id.content)
        listItemView.container = view.findViewById(R.id.container)
        // 设置控件集到convertView
        view.tag = listItemView
      } else {
        listItemView = view.tag as ListItemView
      }

      // 设置数据
      val site = getItem(position)

      val text = site.siteName
      listItemView.content!!.text = text
      listItemView.container!!.setOnClickListener { v ->
        textView!!.text = text

        this@DropDownListView.site = site

        onItemClicked.invoke(site)
        closePopWindow()
      }
      return view
    }

  }

  private class ListItemView {
    internal var content: TextView? = null
    internal var container: View? = null
  }


}
