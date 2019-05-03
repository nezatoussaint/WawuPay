package com.hviewtech.wawupay.common.app

import android.content.Context
import com.hviewtech.wawupay.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader


object SmartRefreshManager {

  fun initialize(context: Context) {
    ClassicsHeader.REFRESH_HEADER_PULLDOWN = context.getString(R.string.srl_header_pulling) //"下拉可以刷新";
    ClassicsHeader.REFRESH_HEADER_REFRESHING = context.getString(R.string.srl_header_refreshing) //"正在刷新...";
    ClassicsHeader.REFRESH_HEADER_LOADING = context.getString(R.string.srl_header_loading) //"正在加载...";
    ClassicsHeader.REFRESH_HEADER_RELEASE = context.getString(R.string.srl_header_release) //"释放立即刷新";
    ClassicsHeader.REFRESH_HEADER_FINISH = context.getString(R.string.srl_header_finish) //"刷新完成";
    ClassicsHeader.REFRESH_HEADER_FAILED = context.getString(R.string.srl_header_failed) //"刷新失败";
    ClassicsHeader.REFRESH_HEADER_LASTTIME = context.getString(R.string.srl_header_update) //"'Last update' M-d HH:mm";
    ClassicsHeader.REFRESH_HEADER_SECOND_FLOOR = context.getString(R.string.srl_header_secondary) //"释放进入二楼"

    ClassicsFooter.REFRESH_FOOTER_PULLUP = context.getString(R.string.srl_footer_pulling) //"上拉加载更多";
    ClassicsFooter.REFRESH_FOOTER_RELEASE = context.getString(R.string.srl_footer_release) //"释放立即加载";
    ClassicsFooter.REFRESH_FOOTER_LOADING = context.getString(R.string.srl_footer_loading) //"正在刷新...";
    ClassicsFooter.REFRESH_FOOTER_REFRESHING = context.getString(R.string.srl_footer_refreshing) //"正在加载...";
    ClassicsFooter.REFRESH_FOOTER_FINISH = context.getString(R.string.srl_footer_finish) //"加载完成";
    ClassicsFooter.REFRESH_FOOTER_FAILED = context.getString(R.string.srl_footer_failed) //"加载失败";
    ClassicsFooter.REFRESH_FOOTER_ALLLOADED = context.getString(R.string.srl_footer_nothing) //"全部加载完成";

    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
      layout.setPrimaryColorsId(R.color.white, R.color.black) //全局设置主题颜色
      ClassicsHeader(context)
      //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
    }
  }
}