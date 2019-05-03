/*
 * Copyright 2015 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hviewtech.wawupay.ui.dialog.profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appeaser.sublimepickerlibrary.SublimePicker
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import com.hviewtech.wawupay.R
import java.text.DateFormat
import java.util.*

class BirthPickerFragment : DialogFragment() {
  // Date & Time formatter used for formatting
  // text on the switcher button
  internal var mDateFormatter: DateFormat
  internal var mTimeFormatter: DateFormat

  // Picker
  lateinit var mSublimePicker: SublimePicker

  // Callback to activity
  internal var mCallback: Callback? = null

  internal var mListener: SublimeListenerAdapter = object : SublimeListenerAdapter() {
    override fun onCancelled() {
      if (mCallback != null) {
        mCallback!!.onCancelled()
      }

      // Should actually be called by activity inside `Callback.onCancelled()`
      dismiss()
    }

    override fun onDateTimeRecurrenceSet(
      sublimeMaterialPicker: SublimePicker,
      selectedDate: SelectedDate?,
      hourOfDay: Int, minute: Int,
      recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
      recurrenceRule: String?
    ) {
      mCallback?.onDateTimeRecurrenceSet(
        selectedDate,
        hourOfDay, minute, recurrenceOption, recurrenceRule
      )

      // Should actually be called by activity inside `Callback.onCancelled()`
      dismiss()
    }
    // You can also override 'formatDate(Date)' & 'formatTime(Date)'
    // to supply custom formatters.
  }


  // Validates & returns SublimePicker options
  private // displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
  // displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;
  // options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
  // options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
  // Enable/disable the date range selection feature
  // Example for setting date range:
  // Note that you can pass a date range as the initial date params
  // even if you have date-range selection disabled. In this case,
  // the user WILL be able to change date-range using the header
  // TextViews, but not using long-press.
  // If 'displayOptions' is zero, the chosen options are not valid
  val options: Pair<Boolean, SublimeOptions>
    get() {
      val options = SublimeOptions()
      var displayOptions = 0

      displayOptions = displayOptions or SublimeOptions.ACTIVATE_DATE_PICKER

      options.pickerToShow = SublimeOptions.Picker.DATE_PICKER

      options.setDisplayOptions(displayOptions)
      options.setCanPickDateRange(false)

      val startCal = Calendar.getInstance()
      startCal.set(1900, 1, 1)
      val endCal = Calendar.getInstance()

      options.setDateRange(startCal.timeInMillis, endCal.timeInMillis)
      return Pair(if (displayOptions != 0) java.lang.Boolean.TRUE else java.lang.Boolean.FALSE, options)
    }

  init {
    // Initialize formatters
    mDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
    mTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH)
    mTimeFormatter.timeZone = TimeZone.getTimeZone("GMT+0")
  }

  // Set activity callback
  fun setCallback(callback: Callback) {
    mCallback = callback
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    /*try {
            //getActivity().getLayoutInflater()
                    //.inflate(R.layout.sublime_recurrence_picker, new FrameLayout(getActivity()), true);
            getActivity().getLayoutInflater()
                    .inflate(R.layout.sublime_date_picker, new FrameLayout(getActivity()), true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }*/
    setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    mSublimePicker = activity!!
      .layoutInflater.inflate(R.layout.sublime_picker, container) as SublimePicker

    mSublimePicker.findViewById<View>(R.id.date_picker_header).visibility = View.GONE
    // Retrieve SublimeOptions
    val arguments = arguments

    // Options
    var options: SublimeOptions? = null
    val optionsPair = this.options

    if (optionsPair.first) { // If options are valid
      options = optionsPair.second
    }
    // Options can be null, in which case, default
    // options are used.
    if (arguments != null) {
      options = arguments.getParcelable("SUBLIME_OPTIONS")
    }

    mSublimePicker.initializePicker(options, mListener)
    return mSublimePicker
  }

  // For communicating with the activity
  interface Callback {
    fun onCancelled()

    fun onDateTimeRecurrenceSet(
      selectedDate: SelectedDate?,
      hourOfDay: Int, minute: Int,
      recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
      recurrenceRule: String?
    )
  }

  fun show(manager: FragmentManager) {
    super.show(manager, javaClass.name)
  }
}
