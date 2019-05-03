package com.hviewtech.wawupay.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.common.action.CancelSwipeBack
import com.hviewtech.wawupay.common.app.TransformationManagers
import com.hviewtech.wawupay.common.bindings.ImageBindings
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.common.utils.ResourceUtils
import com.hviewtech.wawupay.contract.home.MainContract
import com.hviewtech.wawupay.presenter.home.MainPresenter
import com.hviewtech.wawupay.ui.activity.account.ForgetPasswordActivity
import com.hviewtech.wawupay.ui.activity.account.IdentityProfileActivity
import com.hviewtech.wawupay.ui.activity.map.DiscoverActivity
import com.hviewtech.wawupay.ui.activity.profile.MyTransactionsActivity
import com.hviewtech.wawupay.ui.activity.profile.PersonalProfileActivity
import com.hviewtech.wawupay.ui.activity.profile.PersonalQrcodeActivity
import com.hviewtech.wawupay.ui.activity.profile.SettingsActivity
import com.hviewtech.wawupay.ui.activity.qrcode.QRCodeActivity
import com.hviewtech.wawupay.ui.activity.wallet.PersonalWalletActivity
import com.hviewtech.wawupay.ui.dialog.AlertDialog
import com.hviewtech.wawupay.ui.dialog.home.HomeBottomDialog
import com.hviewtech.wawupay.ui.dialog.wallet.PaymentEntranceFragment
import com.hviewtech.wawupay.ui.fragment.home.HomeFragment
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.main_header.view.*
import javax.inject.Inject
import kotlin.reflect.KClass

class MainActivity : BaseMvpActivity(), CancelSwipeBack, MainContract.View, HomeBottomDialog.ActionListener {


  @Inject
  lateinit var mPresenter: MainPresenter

  lateinit var header: View

  companion object {
    val RC_AVATAR = 1
  }

  override fun getLayoutId(): Int {
    return R.layout.act_main
  }

  override fun initialize() {
    super.initialize()


    var fragment = supportFragmentManager.findFragmentById(R.id.container)
    if (fragment == null) {
      fragment = HomeFragment()
      ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.container)
    }


    // DrawerLayout


    val drawerToggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
    drawerToggle.syncState()
    drawerlayout.addDrawerListener(drawerToggle)

    //    ivAvatar.setOnClickListener({ v -> drawerlayout.openDrawer(Gravity.START, true) })

    // NavigationView

    header = navigation.getHeaderView(0)

    val resource = baseContext.resources
    val csl = resource.getColorStateList(R.color.navigation_menu_item_color)
    navigation.setItemTextColor(csl)
    navigation.setItemIconTintList(null)
    navigation.setItemBackgroundResource(R.drawable.bg_nav_item_selector)
    navigation.setCheckedItem(R.id.menu_personal_profile)
    navigation.setNavigationItemSelectedListener({ item ->
      var clazz: KClass<out Activity>? = null
      when (item.getItemId()) {
        R.id.menu_personal_profile -> clazz = PersonalProfileActivity::class
        R.id.menu_my_wallet -> {
          mPresenter.checkPayPasswordExist()
          return@setNavigationItemSelectedListener true
        }
        R.id.menu_transctions -> clazz = MyTransactionsActivity::class
        R.id.menu_setting -> clazz = SettingsActivity::class
      }
      if (clazz != null) {
        goActivity(clazz)
      }
      // Close the navigation drawer when an item is selected.
      //      drawerlayout.closeDrawers()
      drawerlayout.closeDrawer(Gravity.START, false)
      true
    })



    RxRadioGroup.checkedChanges(tabGroup)
      .subscribe {
        if (it == R.id.tab_discover) {
          goActivity(DiscoverActivity::class)
          tabGroup.check(R.id.tab_home)
        }
      }
  }

  override fun onResume() {
    super.onResume()
    val info = HawkExt.info
    if (info != null) {

      header.nickname.value = info.nickname

      setAvatar(info.profilePic)

      header.idnum.value = "ID: ${info.num}"

      header.sex.setImageResource(if (info.sex == 0) R.drawable.icon_sex_woman else R.drawable.icon_sex_man)
    }

    if (info != null && info.idType == 0) {
      AlertDialog(this).builder()
        .setCanceledOnTouchOutside(false)
        .setMsg("You have not perfected the information, to perfect?")
        .setPositiveButton("Yes", {
          goActivity(IdentityProfileActivity::class)
        })
        .setNegativeButton("No", {
          ActivityUtils.toReLogin(this)
        })
        .show()
    }

    mPresenter.getPlatformFee()
  }

  private fun setAvatar(url: String?) {
    ImageBindings.setImageUri(header.avatar, url, transformation = TransformationManagers.cropCircle())
    ImageBindings.setImageUri(avatarDefault, url, transformation = TransformationManagers.cropCircle())
  }

  override fun onBackPressed() {
    if (drawerlayout.isDrawerOpen(Gravity.START)) {
      drawerlayout.closeDrawer(Gravity.START)
      return
    }
    //实现Home键效果
    //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
    val i = Intent(Intent.ACTION_MAIN)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    i.addCategory(Intent.CATEGORY_HOME)
    startActivity(i)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      when (requestCode) {
        RC_AVATAR -> {
          // 图片选择结果回调
          val selectList = PictureSelector.obtainMultipleResult(data)
          // 例如 LocalMedia 里面返回三种path
          // 1.media.getPath(); 为原图path
          // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
          // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
          // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
          if (selectList.size == 1) {
            val media = selectList[0]
            val path: String
            if (media.isCompressed) {
              path = media.compressPath
            } else {
              path = media.cutPath
            }
            val uri = ResourceUtils.parseFile2Uri(path)
            setAvatar(uri)
            mPresenter.uploadAvatar(uri)
          }
        }
      }
    }
  }


  fun extraFunction(view: View) {
    val fragmentTag = HomeBottomDialog.fragTag
    var dialog: HomeBottomDialog? = supportFragmentManager.findFragmentByTag(fragmentTag) as? HomeBottomDialog
    if (dialog == null) {
      dialog = HomeBottomDialog()
      dialog.setActionListener(this)
    }
    dialog.show(supportFragmentManager)
  }

  fun selectAvatarFromAlbum(view: View) {
    PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
      .selectionMode(PictureConfig.SINGLE)
      .previewImage(true)
      .isCamera(true)
      .imageFormat(PictureMimeType.PNG)
      .compress(true)
      .minimumCompressSize(100)
      .withAspectRatio(1, 1)
      .enableCrop(true)
      .circleDimmedLayer(true)
      .showCropFrame(false)
      .showCropGrid(false)
      .freeStyleCropEnabled(true)
      .forResult(RC_AVATAR)
  }

  fun openDrawer(view: View) {
    drawerlayout.openDrawer(Gravity.START, true)
  }

  override fun onAction(isShow: Boolean) {
    tabAdd.visibility = if (isShow) View.INVISIBLE else View.VISIBLE
  }


  override fun scanQRCode() {
    goActivity(QRCodeActivity::class)
  }

  override fun goPersonalWallet() {
    // 检查支付密码是否存在
    mPresenter.checkPayPasswordExist()
  }

  override fun showPersonalQRCode() {

    goActivity(PersonalQrcodeActivity::class)
  }


  override fun showAvatar(url: String?) {
    setAvatar(url)

    mPresenter.modifyUserProfilePic(url)

  }


  override fun checkPayPasswordResult(isExist: Boolean) {

    if (isExist) {
      PaymentEntranceFragment().title("My Wallet")
        .show(supportFragmentManager, {
          goActivity(PersonalWalletActivity::class)
        })
    } else {
      val intent = Intent(mContext, ForgetPasswordActivity::class.java)
      intent.putExtra(ForgetPasswordActivity.TYPE, ForgetPasswordActivity.PAYMENT)
      startActivity(intent)
    }

  }


}