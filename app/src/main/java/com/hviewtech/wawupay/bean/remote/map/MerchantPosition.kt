package com.hviewtech.wawupay.bean.remote.map


data class MerchantPosition(
  /**
   * categoryId : 1
   * description : 店铺描述(非必填)
   * detailedAddress : 成都市的哪个位置
   * latitude : 30.234251575359643
   * longitude : 103.0972346663475
   * profilePic : 20180422124209324.jpg
   * shopName : 店铺名称修改了
   */

  var categoryId: Int = 0,
  var description: String? = null,
  var detailedAddress: String? = null,
  var latitude: Double = 0.toDouble(),
  var longitude: Double = 0.toDouble(),
  var profilePic: String? = null,
  var shopName: String? = null
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this.javaClass !== other.javaClass) return false

    val position = other as MerchantPosition?

    if (java.lang.Double.compare(position!!.latitude, latitude) != 0) return false
    if (java.lang.Double.compare(position.longitude, longitude) != 0) return false
    return if (shopName != null) shopName == position.shopName else position.shopName == null
  }

  override fun hashCode(): Int {
    var result: Int
    var temp: Long
    temp = java.lang.Double.doubleToLongBits(latitude)
    result = (temp xor temp.ushr(32)).toInt()
    temp = java.lang.Double.doubleToLongBits(longitude)
    result = 31 * result + (temp xor temp.ushr(32)).toInt()
    result = 31 * result + if (shopName != null) shopName!!.hashCode() else 0
    return result
  }
}