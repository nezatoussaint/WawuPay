package com.hviewtech.wawupay.common.storage

import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.utils.AESUtils
import com.orhanobut.hawk.Encryption

class AESEncryption : Encryption {

  val key: ByteArray

  init {
    val datas = mutableListOf<Byte>()
    for (byte in Constants.Secure.KEY) {
      val data = (byte.toInt() xor 1).toByte()
      datas.add(data)
    }
    key = datas.toByteArray()
  }

  override fun init(): Boolean {

    return true
  }

  @Throws(Exception::class)
  override fun encrypt(key: String, value: String): String {
    return encrypt(value.toByteArray())
  }

  @Throws(Exception::class)
  override fun decrypt(key: String, value: String): String {
    return decrypt(value)
  }

  internal fun encrypt(bytes: ByteArray): String {

    return AESUtils.encText(bytes, key)
  }

  internal fun decrypt(value: String): String {
    return AESUtils.decText(value, key)
  }
}
