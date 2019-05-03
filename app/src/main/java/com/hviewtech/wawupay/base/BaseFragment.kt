package com.hviewtech.wawupay.base

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hviewtech.wawupay.common.ext.DEFAULT
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.utils.DialogUtils
import com.hviewtech.wawupay.common.utils.InjectUtils
import com.hviewtech.wawupay.contract.Contract
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.LifecycleProvider
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
abstract class BaseFragment : Fragment(), Contract.View, EasyPermissions.PermissionCallbacks {

  protected val provider: LifecycleProvider<Lifecycle.Event> = AndroidLifecycle.createLifecycleProvider(this)
  protected lateinit var mContext: Context

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    mContext = context!!
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return LayoutInflater.from(container?.context).inflate(getLayoutId(), container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    InjectUtils.inject(this, view)
    initialize()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    InjectUtils.unInject()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    // Forward results to EasyPermissions
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
    // Some permissions have been granted
    // ...
  }

  override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
    // Some permissions have been denied
    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      AppSettingsDialog.Builder(this).build().show()
    }
  }

  open protected fun initialize() {
  }

  abstract fun getLayoutId(): Int

  override fun showloading() {
    DialogUtils.showProgressLoading(mContext, provider.bindToLifecycle())
  }

  override fun dismissloading(error: Boolean, handler: () -> Unit) {
    if (error) {
      DialogUtils.dismissErrorLoading(provider.bindToLifecycle(), handler)
    } else {
      if (handler.hashCode() == Unit.DEFAULT.hashCode()) {
        DialogUtils.dismissProgressLoading()
      } else {
        DialogUtils.dismissProgressLoading(provider.bindToLifecycle(), handler)
      }

    }
  }

  override fun showError(msg: String?) {
    toast(msg)
  }

}