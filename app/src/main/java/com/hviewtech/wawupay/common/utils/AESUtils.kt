package com.hviewtech.wawupay.common.utils


import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {
  val ivk = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 3, 2, 1)

  val transformation = Base64Utils.decodeString("QUVTL0NCQy9QS0NTNVBhZGRpbmc=")
  val algorithm = Base64Utils.decodeString("QUVT")

  private fun encBytes(srcBytes: ByteArray, key: ByteArray): ByteArray {
    try {
      val cipher = Cipher.getInstance(transformation)
      val skeySpec = SecretKeySpec(key, algorithm)
      val iv = IvParameterSpec(ivk)
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
      return cipher.doFinal(srcBytes)
    } catch (e: NoSuchAlgorithmException) {
      e.printStackTrace()
    } catch (e: NoSuchPaddingException) {
      e.printStackTrace()
    } catch (e: InvalidKeyException) {
      e.printStackTrace()
    } catch (e: BadPaddingException) {
      e.printStackTrace()
    } catch (e: IllegalBlockSizeException) {
      e.printStackTrace()
    }

    return "".toByteArray()
  }


  fun encText(sSrc: String, key: ByteArray): String {
    return encText(sSrc.toByteArray(charset("utf-8")), key)
  }

  fun encText(srcBytes: ByteArray, key: ByteArray): String {
    try {
      val encrypted = encBytes(srcBytes, key)
      return Base64Utils.encode(encrypted)
    } catch (e: UnsupportedEncodingException) {
      e.printStackTrace()
    }

    return ""
  }

  private fun decBytes(srcBytes: ByteArray, key: ByteArray): ByteArray {
    try {
      val cipher = Cipher.getInstance(transformation)
      val skeySpec = SecretKeySpec(key, algorithm)
      val iv = IvParameterSpec(ivk)
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
      return cipher.doFinal(srcBytes)
    } catch (e: NoSuchAlgorithmException) {
      e.printStackTrace()
    } catch (e: NoSuchPaddingException) {
      e.printStackTrace()
    } catch (e: IllegalBlockSizeException) {
      e.printStackTrace()
    } catch (e: InvalidKeyException) {
      e.printStackTrace()
    } catch (e: BadPaddingException) {
      e.printStackTrace()
    }

    return "".toByteArray()
  }

  @Throws(Exception::class)
  fun decText(sSrc: String, key: ByteArray): String {
    val srcBytes = Base64Utils.decode(sSrc)
    val decrypted = decBytes(srcBytes, key)
    return String(decrypted, Charset.defaultCharset())
  }


}



