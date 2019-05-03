package com.hviewtech.wawupay.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseFragment
import com.hviewtech.wawupay.bean.local.App
import com.hviewtech.wawupay.bean.local.Tool
import com.hviewtech.wawupay.ui.adapter.home.ToolAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.shizhefei.view.indicator.Indicator
import com.shizhefei.view.indicator.slidebar.DrawableBar
import com.shizhefei.view.indicator.transition.OnTransitionTextListener
import kotlinx.android.synthetic.main.frag_home.*

class HomeFragment : BaseFragment() {

  private var mIgnoreScrollEvent: Boolean = false
  override fun getLayoutId(): Int {
    return R.layout.frag_home
  }


  override fun initialize() {
    super.initialize()

    val utitlities = arrayListOf(
      App("Phone Top Up", R.drawable.phone_topup_icon),
      App("Water", R.drawable.water_icon),
      App("Electricity", R.drawable.electricity_icon)
    )


    val transportation = arrayListOf(
      App("Bus Ticket", R.drawable.bus_ticket_icon),
      App("TWAZA", R.drawable.truck_icon),
      App("School Bus", R.drawable.schoolbus),
      App("Motor Cycle", R.drawable.motorcycle_icon)
    )




    val subscriptions = arrayListOf(
      App("Star Times", R.drawable.startimes)
    )
    val education = arrayListOf(
      App("School Fee", R.drawable.user_icon),
      App("Donation", R.drawable.donation_icon)
    )

    val thirdyparty = arrayListOf(App("SANEZA", 0))


    val tools = arrayListOf(
      Tool("UTITLITIES", utitlities), Tool("TRANSPORTATION", transportation),
      Tool("SUBSCRIPTIONS", subscriptions), Tool("EDUCATION", education),
      Tool("THIRDY PARTY", thirdyparty)
    )


    val layoutManager = LinearLayoutManager(mContext)
    recyclerView.layoutManager = layoutManager
    val toolAdapter = ToolAdapter(mContext)
    toolAdapter.setDatas(tools)
    recyclerView.adapter = toolAdapter
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          mIgnoreScrollEvent = false
        }
      }

      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (mIgnoreScrollEvent) {
          return
        }
        //滑动RecyclerView list的时候，根据最上面一个Item的position来切换tab

        val position = layoutManager.findFirstVisibleItemPosition()

        //        var tabPosition = -1
        //
        //        // mScrollPosition里面的位置  adapter为偶数时位置不变  基数时+1 这样才能保证下一个item是SubTitleAdapter的item
        //        for (i in 0 until mScrollPosition.size()) {
        //          val key = mScrollPosition.keyAt(i)
        //          val value = mScrollPosition.valueAt(i)
        //          if (position >= value && tabPosition < key) {
        //            tabPosition = key
        //          }
        //        }

        if (position != -1) {
          tabLayout.setCurrentItem(position)
        }


      }
    })

    tabLayout.setOnIndicatorItemClickListener(object : Indicator.OnIndicatorItemClickListener {

      override fun onItemClick(clickItemView: View, position: Int): Boolean {
        //点击tab的时候，RecyclerView自动滑到该tab对应的item位置
        scrollToTabApp(position)
        return false
      }

      private fun scrollToTabApp(position: Int) {
        mIgnoreScrollEvent = true
        if (position != -1) {
          layoutManager.scrollToPositionWithOffset(position, 0)
        }

      }
    })
    tabLayout.setAdapter(IndicatorAdapter(tools))
    //    val colorBar = ColorBar(mContext.applicationContext, 0xFFA59ECA.toInt(), 5)
    //    colorBar.setWidth(DensityUtil.dp2px(36f))
    //    tabLayout.setScrollBar(colorBar)

    val drawableBar = DrawableBar(mContext.applicationContext, R.drawable.underline)
    tabLayout.setScrollBar(drawableBar)
    val selectColor = 0xFFA59ECA.toInt()
    val unSelectColor = 0xFF999999.toInt()
    tabLayout.setOnTransitionListener(OnTransitionTextListener().setColor(selectColor, unSelectColor))


  }

  override fun onResume() {
    super.onResume()


  }

  private inner class IndicatorAdapter constructor(val datas: List<Tool> = arrayListOf()) :
    Indicator.IndicatorAdapter() {

    override fun getCount(): Int {
      return datas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      var view = convertView
      if (view == null) {
        val textView = TextView(parent.context)
        textView.layoutParams = FrameLayout.LayoutParams(DensityUtil.dp2px(100f), ViewGroup.LayoutParams.MATCH_PARENT)
        //                    textView.setWidth(DensityUtil.dp2px(70));
        //                    textView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        textView.gravity = Gravity.CENTER
        textView.textSize = 10f
        view = textView
      }
      val textView = view as TextView?
      //用了固定宽度可以避免TextView文字大小变化，tab宽度变化导致tab抖动现象
      val category = datas[position].category
      textView?.setText(category)
      return view
    }
  }
}