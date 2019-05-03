package com.hviewtech.wawupay.common.ext

import android.app.Activity
import android.support.annotation.Size
import android.support.v4.app.Fragment
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

fun Activity.requestPermissions(rationale: Any?, requestCode: Int, @Size(min = 1) vararg perms: String) {
  EasyPermissions.requestPermissions(
    PermissionRequest.Builder(this, requestCode, *perms).apply {
      if (rationale is Int) {
        setRationale(rationale)
      } else if (rationale is String) {
        setRationale(rationale)
      }
    }.build()
  )
}

fun Fragment.requestPermissions(rationale: Any?, requestCode: Int, @Size(min = 1) vararg perms: String) {
  EasyPermissions.requestPermissions(
    PermissionRequest.Builder(this, requestCode, *perms).apply {
      if (rationale is Int) {
        setRationale(rationale)
      } else if (rationale is String) {
        setRationale(rationale)
      }
    }.build()
  )
}