package com.hviewtech.wawupay.common.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.View
import com.hviewtech.wawupay.common.utils.ActivityUtils
import kotlin.reflect.KClass


fun Activity.goActivities(vararg classes: KClass<out Activity>, isFinish: Boolean = false) {

  var intent: Intent
  val intents = arrayListOf<Intent>()
  for (clazz in classes) {
    intent = Intent(this, clazz.java)
    intents.add(intent)
  }
  goIntents(intents.toArray(arrayOf<Intent>()), isFinish)
}

fun Activity.goIntents(intents: Array<Intent>, isFinish: Boolean = false) {

  ActivityCompat.startActivities(this, intents, null)
  if (isFinish) {
    ActivityCompat.finishAffinity(this)
  }
}


fun Activity.goActivity(clazz: KClass<out Activity>, requestCode: Int = -1, isFinish: Boolean = false) {
  val intent = Intent(this, clazz.java)
  goIntent(intent, requestCode, isFinish)
}


fun Fragment.goActivity(clazz: KClass<out Activity>, isFinish: Boolean = false) {
  val intent = Intent(context, clazz.java)
  goIntent(intent, isFinish)
}

fun Context.goActivity(clazz: KClass<out Activity>, isFinish: Boolean = false) {
  val intent = Intent(this, clazz.java)
  goIntent(intent, isFinish)
}

fun View.goActivity(clazz: KClass<out Activity>, isFinish: Boolean = false) {
  val intent = Intent(context, clazz.java)
  goIntent(intent, isFinish)
}


fun Activity.goIntent(intent: Intent, requestCode: Int = -1, isFinish: Boolean = false) {
  if (requestCode == -1) {
    startActivity(intent)
  } else {
    startActivityForResult(intent, requestCode)
  }
  if (isFinish) {
    ActivityCompat.finishAffinity(this)
  }
}

fun Fragment.goIntent(intent: Intent, isFinish: Boolean = false, requestCode: Int = -1) {
  if (requestCode == -1) {
    startActivity(intent)
  } else {
    startActivityForResult(intent, requestCode)
  }
  if (isFinish) {
    val activity = activity
    activity ?: return
    ActivityCompat.finishAffinity(activity)
  }
}

fun Context.goIntent(intent: Intent, isFinish: Boolean = false) {
  ActivityCompat.startActivity(this, intent, null)
  if (isFinish) {
    val activity = ActivityUtils.getActivity(this)
    activity ?: return
    ActivityCompat.finishAffinity(activity)
  }
}


fun View.goIntent(intent: Intent, isFinish: Boolean = false) {
  ActivityCompat.startActivity(context, intent, null)
  if (isFinish) {
    val activity = ActivityUtils.getActivity(context)
    activity ?: return
    ActivityCompat.finishAffinity(activity)
  }
}