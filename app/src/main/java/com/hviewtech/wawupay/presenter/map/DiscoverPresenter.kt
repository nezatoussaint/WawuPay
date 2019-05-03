package com.hviewtech.wawupay.presenter.map

import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.bean.remote.map.CategoryList
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.map.DiscoverContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.ApiModel
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import org.reactivestreams.Subscription

import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class DiscoverPresenter @Inject constructor(private val mApiModel: ApiModel, private val mUserModel: UserModel) :
  BasePresenter<DiscoverContract.View>(), DiscoverContract.Presenter {
  private var mMerchantDisposable: Subscription? = null

  fun getMerchantPosition(longitude: Double, latitude: Double, km: Int, categoryId: Int) {
    if (mMerchantDisposable != null) {
      mMerchantDisposable!!.cancel()
    }
    val num = HawkExt.info?.num
    num ?: return
    mUserModel.postGetMerchantPosition(num, longitude, latitude, km, categoryId)
      .bindToLifecycle(this)
      .compose<MerchantPositionList>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<MerchantPositionList>(mView) {

        override fun onSubscribe(s: Subscription) {
          super.onSubscribe(s)
          mMerchantDisposable = s
        }

        override fun onNext(data: MerchantPositionList) {
          mView?.showMerchantList(data)
        }
      })
  }

  fun initCategories() {

    mApiModel.postGetShopCategories()
      .bindToLifecycle(this)
      .compose<CategoryList>(HttpTransformer())
      .subscribe(object : SimpleSubscriber<CategoryList?>() {
        override fun onNext(data: CategoryList?) {
          mView?.updateCategories(data?.list)
        }
      })
  }
}
