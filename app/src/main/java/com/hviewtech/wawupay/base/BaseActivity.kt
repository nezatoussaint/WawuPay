package com.hviewtech.wawupay.base

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.graphics.PixelFormat
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.support.annotation.Size
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.blankj.rxbus.RxBus
import com.hviewtech.wawupay.AppApplication
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.bean.local.Network
import com.hviewtech.wawupay.common.action.CancelSwipeBack
import com.hviewtech.wawupay.common.action.CustomStatusBar
import com.hviewtech.wawupay.common.action.Exit
import com.hviewtech.wawupay.common.ext.DEFAULT
import com.hviewtech.wawupay.common.ext.logd
import com.hviewtech.wawupay.common.ext.requestPermissions
import com.hviewtech.wawupay.common.ext.toast
import com.hviewtech.wawupay.common.utils.DialogUtils
import com.hviewtech.wawupay.common.utils.InjectUtils
import com.hviewtech.wawupay.common.utils.NetUtils
import com.hviewtech.wawupay.common.utils.StatusBarUtil
import com.hviewtech.wawupay.contract.Contract
import com.hviewtech.wawupay.ui.widget.TitleView
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.LifecycleProvider
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import top.bingoz.swipebacklib.SwipeBackActivityHelper
import top.bingoz.swipebacklib.SwipeBackLayout
import top.bingoz.swipebacklib.tools.Util


/**
 * @author su
 * @date 2018/11/23
 * @descrition 基类
 */
abstract class BaseActivity : AppCompatActivity(), Contract.View, EasyPermissions.PermissionCallbacks {
  protected val provider: LifecycleProvider<Lifecycle.Event> = AndroidLifecycle.createLifecycleProvider(this)

  protected val mContext = this;
  private var exitApplicationTime: Long = 0
  protected var mHelper: SwipeBackActivityHelper? = null
  protected var mSwipeBackLayout: SwipeBackLayout? = null
  protected var mCheckNetWork = true //默认检查网络状态
  var mTipView: View? = null
  var mWindowManager: WindowManager? = null
  var mLayoutParams: WindowManager.LayoutParams? = null

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(getLayoutId())
    InjectUtils.inject(this)

    // 要自定义SwipeBack  实现该接口
    if (this is CancelSwipeBack) {

    } else {
      //是否支持滑动返回
      mHelper = SwipeBackActivityHelper(this)
      //是否支持缩放动画
      mHelper?.onActivityCreate(isSupportFinishAnim())

      mSwipeBackLayout = mHelper?.getSwipeBackLayout()

      mSwipeBackLayout?.setEnableGesture(isSupportSwipeBack())

      mSwipeBackLayout?.setDirectionMode(SwipeBackLayout.FROM_LEFT)
    }

    // 要自定义StatusBar  实现该接口
    if (this is CustomStatusBar) {

    } else {
      // 默认透明状态栏, 颜色由TitleView控制
      // 如果包含状态栏占位View就设置status_bar_height
      //判断当前设备版本号是否为4.4以上，如果是，则通过调用setTranslucentStatus让状态栏变透明
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        StatusBarUtil.setTranslucentStatus(true, window)
        //        StatusBarUtil.setTransparent(this)
      }
    }

    initTipView()



    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val connectivityManager =
        AppApplication.app.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager?

      connectivityManager?.requestNetwork(
        NetworkRequest.Builder().build(),
        object : ConnectivityManager.NetworkCallback() {
          override fun onAvailable(network: android.net.Network?) {
            super.onAvailable(network)
            hasNetWork(true)
          }


          override fun onLost(network: android.net.Network?) {
            super.onLost(network)
            hasNetWork(false)
          }
        })
    } else {

      RxBus.getDefault().subscribeSticky(this, object : RxBus.Callback<Network>() {
        override fun onEvent(network: Network?) {
          hasNetWork(network?.hasNetwork ?: false)
        }
      })

    }


    initialize()

  }

  protected open fun isSupportFinishAnim(): Boolean = true
  protected open fun isSupportSwipeBack(): Boolean = true


  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    logd("onPostCreate  mActivity ========" + javaClass.getSimpleName())
    mHelper?.onPostCreate()
  }

  override fun onEnterAnimationComplete() {
    super.onEnterAnimationComplete()
    if (mSwipeBackLayout != null) {

      if (mSwipeBackLayout!!.finishAnim && !mSwipeBackLayout!!.mIsActivitySwipeing) {
        Util.convertActivityFromTranslucent(this)
        mSwipeBackLayout!!.mIsActivityTranslucent = false
        logd("onEnterAnimationComplete  mActivity ========" + javaClass.getSimpleName())
      }
    }
  }

  override fun onResume() {
    super.onResume()
    // 如果使用了StatusBarUtil.setTransparent(this)
    // 用于状态栏白色
    // ActivityUtils.adaptNavigationBar(this)
    //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
    hasNetWork(NetUtils.isConnected(mContext));
  }

  override fun onDestroy() {
    RxBus.getDefault().unregister(this)
    InjectUtils.unInject()
    super.onDestroy()

  }

  override fun onBackPressed() {
    if (this is Exit) {
      val now = SystemClock.uptimeMillis()
      if (now - exitApplicationTime <= 2000) {
        super.onBackPressed()

        System.exit(0)
      } else {
        toast("再按一次退出WawuPay")
        exitApplicationTime = now
      }
    } else {
      super.onBackPressed()
    }
  }

  @Override
  override fun finish() {
    //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
    if (mTipView != null && mTipView!!.getParent() != null) {
      mWindowManager?.removeView(mTipView);
    }
    super.finish();
    mHelper?.finish()
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


  fun judgePermissions(action: () -> Unit, requestCode: Int, @Size(min = 1) vararg perms: String) {
    if (EasyPermissions.hasPermissions(this, *perms)) {
      action.invoke()
    } else {
      // Do not have permissions, request them now
      requestPermissions(null, requestCode, *perms)
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


  @Synchronized
  private fun hasNetWork(has: Boolean) {
    logd("network: $has")
    if (isCheckNetWork()) {
      if (has) {
        if (mTipView != null && mTipView!!.getParent() != null) {
          if (!isDestroyed) {
            mWindowManager?.removeView(mTipView)
          }
          logd("remove tip view")
        }
      } else {
        if (mTipView != null && mTipView!!.getParent() == null) {
          if (!isDestroyed) {
            mWindowManager?.addView(mTipView, mLayoutParams)
          }
          logd("add tip view")
        }
      }
    }
  }

  fun setCheckNetWork(checkNetWork: Boolean) {
    mCheckNetWork = checkNetWork
  }

  fun isCheckNetWork(): Boolean {
    return mCheckNetWork
  }

  private fun initTipView() {
    val inflater = layoutInflater
    mTipView = inflater.inflate(R.layout.layout_network_tip, null) //提示View布局
    mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager?
    mLayoutParams = WindowManager.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.TYPE_APPLICATION,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
      PixelFormat.TRANSLUCENT
    )
    //使用非CENTER时，可以通过设置XY的值来改变View的位置
    mLayoutParams?.gravity = Gravity.TOP
    mLayoutParams?.x = 0

    if (hasTitleView(window.decorView)) {
      mLayoutParams?.y = DensityUtil.dp2px(46f)
    }
  }

  private fun hasTitleView(view: View): Boolean {
    if (view is ViewGroup) {
      for (i in 0..view.childCount) {
        val child = view.getChildAt(i)
        if (child is TitleView) {
          return true
        } else if (child is ViewGroup) {
          return hasTitleView(child)
        }
      }
    }
    return false;
  }
}