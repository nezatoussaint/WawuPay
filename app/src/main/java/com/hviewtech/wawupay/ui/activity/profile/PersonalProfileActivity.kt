package com.hviewtech.wawupay.ui.activity.profile

import android.view.View
import android.widget.LinearLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.common.app.TransformationManagers
import com.hviewtech.wawupay.common.bindings.ImageBindings
import com.hviewtech.wawupay.common.ext.goActivity
import com.hviewtech.wawupay.common.ext.value
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.profile.PersonalProfileContract
import com.hviewtech.wawupay.presenter.profile.PersonalProfilePresenter
import com.hviewtech.wawupay.ui.dialog.profile.BirthPickerFragment
import com.hviewtech.wawupay.ui.dialog.profile.UpdateNameFragment
import com.hviewtech.wawupay.ui.widget.citypicker.WawuAddressProvider
import kotlinx.android.synthetic.main.act_personal_profile.*
import xyz.icoder.citypicker.AddressSelector
import xyz.icoder.citypicker.OnAddressSelectedListener
import xyz.icoder.citypicker.model.City
import xyz.icoder.citypicker.model.County
import xyz.icoder.citypicker.model.Province
import xyz.icoder.citypicker.model.Street
import java.util.*
import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class PersonalProfileActivity : BaseMvpActivity(), PersonalProfileContract.View, OnAddressSelectedListener {

  @Inject
  lateinit var mPresenter: PersonalProfilePresenter

  private var mWhichSex = 0

  private var mProvinceCities: ProvinceCitys? = null
  private var mLocation: String? = null

  override fun getLayoutId(): Int {
    return R.layout.act_personal_profile
  }

  override fun initialize() {
    super.initialize()

    val info = HawkExt.info
    if (info != null) {

      ImageBindings.setImageUri(avatar, info.profilePic, transformation = TransformationManagers.cropCircle())
      nickname.value = info.nickname
      idnum.value = info.num


      sex.value = if (info.sex == 1) "male" else "female"

      birthday.value = info.dateBirth
    }
  }

  override fun onResume() {
    super.onResume()
    mPresenter.initProvinceCities(false)
  }

  fun showMyQRCode(view: View) {
    goActivity(PersonalQrcodeActivity::class)
  }

  fun updateName(view: View) {

    UpdateNameFragment().title("Names").show(supportFragmentManager, { content ->
      nickname.value = content
      mPresenter.updateNickName(content)
    })
  }

  fun updateSex(view: View) {
    val items = arrayOf<String>("female", "male")
    MaterialDialog.Builder(this)
      .title("Sex")
      .items(*items)
      .autoDismiss(true)
      .itemsCallbackSingleChoice(
        mWhichSex
      ) { dialog, view, which, text ->
        mWhichSex = which
        sex.value = items[which]
        mPresenter.updateSex(which)
        true // allow selection
      }.show()
  }

  fun updateBirthday(view: View) {

    val fragment = BirthPickerFragment()
    fragment.setCallback(object : BirthPickerFragment.Callback {
      override fun onCancelled() {

      }

      override fun onDateTimeRecurrenceSet(
        selectedDate: SelectedDate?,
        hourOfDay: Int,
        minute: Int,
        recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
        recurrenceRule: String?
      ) {
        val calendar = selectedDate?.firstDate
        calendar ?: return
        birthday.value =
            String.format(
              Locale.getDefault(),
              "%d-%02d-%02d",
              calendar.get(Calendar.YEAR),
              calendar.get(Calendar.MONTH) + 1,
              calendar.get(Calendar.DAY_OF_MONTH)
            )
      }
    })
    fragment.show(supportFragmentManager)
  }

  fun updateLocation(view: View? = null) {
    if (mProvinceCities == null) {
      mPresenter.initProvinceCities(true)
      return
    }
    val selector = AddressSelector(this)
    selector.onAddressSelectedListener = this
    selector.setAddressProvider(WawuAddressProvider(mProvinceCities))
    val root = View.inflate(mContext, R.layout.dialog_location, null) as LinearLayout
    root.addView(selector.view)
    MaterialDialog.Builder(this)
      .title("Location")
      .customView(root, false)
      .positiveText(android.R.string.ok)
      .negativeText(android.R.string.cancel)
      .onPositive { dialog, which -> location.value = mLocation }
      .show()
  }

  override fun updateProvinceCities(showProgress: Boolean, result: ProvinceCitys?) {
    this.mProvinceCities = result
    if (showProgress) {
      updateLocation()
    }
  }


  override fun onAddressSelected(province: Province?, city: City?, county: County?, street: Street?) {
    if (province == null) {
      mLocation = null
    } else if (city == null) {
      mLocation = String.format("%s", province.name)
    } else if (county == null) {
      mLocation = String.format("%s %s", province.name, city.name)
    } else if (street == null) {
      mLocation = String.format("%s %s %s", province.name, city.name, county.name)
    } else {
      mLocation = String.format("%s %s %s %s", province.name, city.name, county.name, street.name)
    }
  }
}
