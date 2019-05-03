package com.hviewtech.wawupay.bean.local

data class Tool(

  var category: String,
  var apps: List<App> = arrayListOf()

) {
}