package com.hviewtech.wawupay.presenter.profile


import com.hviewtech.wawupay.base.BasePresenter
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.profile.ReportContract
import com.hviewtech.wawupay.data.http.subscriber.SimpleSubscriber
import com.hviewtech.wawupay.data.http.transformer.HttpTransformer
import com.hviewtech.wawupay.model.UserModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/25
 * @description
 */

class ReportPresenter @Inject constructor(private val mUserModel: UserModel) : BasePresenter<ReportContract.View>(),
  ReportContract.Presenter {

  fun complaintInfo(title: String, content: String, orderNo: String?) {
    orderNo ?: return
    val userId = HawkExt.info?.userId
    userId ?: return
    mUserModel.postUserComplaintInfo(userId, title, content, orderNo)
      .bindToLifecycle(this)
      .compose(HttpTransformer())
      .subscribe(object : SimpleSubscriber<Any?>(mView) {
        override fun onNext(data: Any?) {
          mView?.showReportSuccess()
        }
      })
  }
}
