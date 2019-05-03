package com.hviewtech.wawupay.common.storage

import android.content.Context
import android.content.SharedPreferences
import com.hviewtech.wawupay.common.utils.Base64Utils
import com.orhanobut.hawk.Storage

class SharedPreferencesStorage : Storage {

  private val preferences: SharedPreferences

  private val editor: SharedPreferences.Editor
    get() = preferences.edit()

  constructor(context: Context) {
    //  创建_data.xml
    preferences = context.getSharedPreferences(Base64Utils.decodeString("X2RhdGE="), Context.MODE_PRIVATE)
  }

  override fun <T> put(key: String, value: T) {
    editor.putString(key, value.toString()).apply()
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T> get(key: String): T {
    return preferences.getString(key, null) as T
  }

  override fun delete(key: String) {
    editor.remove(key).apply()
  }

  override fun contains(key: String): Boolean {
    return key in preferences
  }

  override fun deleteAll() {
    return editor.clear().apply()
  }

  override fun count(): Long {
    return preferences.all.size.toLong()
  }

}