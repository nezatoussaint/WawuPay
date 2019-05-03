package com.hviewtech.wawupay.ui.activity.ticket

import android.content.Intent
import android.view.View
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseActivity
import com.hviewtech.wawupay.bean.local.Traveller
import com.hviewtech.wawupay.common.ext.value
import kotlinx.android.synthetic.main.act_add_traveller.*

class AddTravellerActivity : BaseActivity() {

  companion object {
    const val TRAVELLER = "traveller"
  }

  override fun getLayoutId(): Int {
    return R.layout.act_add_traveller
  }


  override fun initialize() {
    super.initialize()


  }


  fun addTraveller(view: View) {

    val firstName = etFirstName.value
    val lastName = etLastName.value

    val phoneNumber = etPhoneNumber.value


    val traveller = Traveller(firstName, lastName, phoneNumber)


    val intent = Intent()
    intent.putExtra(TRAVELLER, traveller)
    setResult(RESULT_OK, intent)
    finish()

  }
}