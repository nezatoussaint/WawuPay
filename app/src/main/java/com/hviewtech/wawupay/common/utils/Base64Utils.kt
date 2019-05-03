package com.hviewtech.wawupay.common.utils

object Base64Utils {

  // The line separator string of the operating system.
  private val systemLineSeparator = System.getProperty("line.separator")
  private val fixChar = '='
  private val fChar1 = '+'
  private val fChar2 = '/'

  // Mapping table from 6-bit nibbles to Base64Utils characters.
  private val map1 = CharArray(64)

  init {
    var i = 0;
    for (c in 'A'..'Z') {
      map1[i++] = c;
    }
    for (c in 'a'..'z') {
      map1[i++] = c;
    }
    for (c in '0'..'9') {
      map1[i++] = c;
    }
    map1[i++] = fChar1;
    map1[i] = fChar2;
  }

  // Mapping table from Base64Utils characters to 6-bit nibbles.
  private val map2 = ByteArray(128)

  init {
    for (i in map2.indices)
      map2[i] = -1
    for (i in 0..63)
      map2[map1[i].toInt()] = i.toByte()
  }

  /**
   * Encodes a string into Base64Utils format. No blanks or line breaks are
   * inserted.
   *
   * @param s A String to be encoded.
   * @return A String containing the Base64Utils encoded data.
   */
  fun encodeString(s: String): String {
    return encode(s.toByteArray())
  }


  /**
   * Encodes a string into Base64Utils format. No blanks or line breaks are
   * inserted.
   *
   * @param s      A String to be encoded.
   * @param encode String的编码
   * @return A String containing the Base64Utils encoded data.
   * @throws UnsupportedEncodingException
   * @throws UnsupportedEncodingException
   */
  @Throws(Exception::class)
  fun encodeString(s: String, encode: String): String {
    val result = encode(s.toByteArray(charset(encode)))
    return result
  }

  /**
   * Encodes a byte array into Base 64 format and breaks the output into lines
   * of 76 characters. This method is compatible with
   * `sun.misc.BASE64Encoder.encodeBuffer(byte[])`.
   *
   * @param in An array containing the data bytes to be encoded.
   * @return A String containing the Base64Utils encoded data, broken into lines.
   */
  fun encodeLines(data: ByteArray): String {
    return encodeLines(data, 0, data.size, 76, systemLineSeparator)
  }

  /**
   * Encodes a byte array into Base 64 format and breaks the output into
   * lines.
   *
   * @param in            An array containing the data bytes to be encoded.
   * @param iOff          Offset of the first byte in data to be processed.
   * @param iLen          Number of bytes to be processed in data, starting
   * at `iOff`.
   * @param lineLen       Line length for the output data. Should be a multiple of 4.
   * @param lineSeparator The line separator to be used to separate the output lines.
   * @return A String containing the Base64Utils encoded data, broken into lines.
   */
  fun encodeLines(
    data: ByteArray, iOff: Int, iLen: Int,
    lineLen: Int, lineSeparator: String
  ): String {
    val blockLen = lineLen * 3 / 4
    if (blockLen <= 0) {
      throw IllegalArgumentException()
    }
    val lines = (iLen + blockLen - 1) / blockLen
    val bufLen = (iLen + 2) / 3 * 4 + lines * lineSeparator.length
    val buf = StringBuilder(bufLen)
    var ip = 0
    while (ip < iLen) {
      val l = Math.min(iLen - ip, blockLen)
      buf.append(encode(data, iOff + ip, l))
      buf.append(lineSeparator)
      ip += l
    }
    return buf.toString()
  }

  // /**
  // * Encodes a byte array into Base64Utils format.
  // * No blanks or line breaks are inserted in the output.
  // * @param in An array containing the data bytes to be encoded.
  // * @return A character array containing the Base64Utils encoded data.
  // */
  // public static char[] encode (byte[] in) {
  // return encode(in, 0, in.length);
  // }

  /**
   * Encodes a byte array into Base64Utils format. No blanks or line breaks are
   * inserted in the output.
   *
   * @param in An array containing the data bytes to be encoded.
   * @return A String containing the Base64Utils encoded data.
   */
  fun encode(data: ByteArray): String {
    return String(encode(data, 0, data.size))
  }

  /**
   * Encodes a byte array into Base64Utils format. No blanks or line breaks are
   * inserted in the output.
   *
   * @param in   An array containing the data bytes to be encoded.
   * @param iLen Number of bytes to process in data.
   * @return A character array containing the Base64Utils encoded data.
   */
  fun encode(data: ByteArray, iLen: Int): CharArray {
    return encode(data, 0, iLen)
  }

  /**
   * Encodes a byte array into Base64Utils format. No blanks or line breaks are
   * inserted in the output.
   *
   * @param in   An array containing the data bytes to be encoded.
   * @param iOff Offset of the first byte in data to be processed.
   * @param iLen Number of bytes to process in data, starting at
   * `iOff`.
   * @return A character array containing the Base64Utils encoded data.
   */
  fun encode(data: ByteArray, iOff: Int, iLen: Int): CharArray {
    val oDataLen = (iLen * 4 + 2) / 3 // output length without padding
    val oLen = (iLen + 2) / 3 * 4 // output length including padding
    val out = CharArray(oLen)
    var ip = iOff
    val iEnd = iOff + iLen
    var op = 0
    while (ip < iEnd) {
      val i0 = data[ip++].toInt() and 0xff
      val i1 = if (ip < iEnd) (data[ip++].toInt() and 0xff) else 0
      val i2 = if (ip < iEnd) (data[ip++].toInt() and 0xff) else 0
      val o0 = i0.ushr(2)
      val o1 = ((i0 and 3) shl 4) or (i1 ushr 4)
      val o2 = ((i1 and 0xf) shl 2) or (i2 ushr 6)
      val o3 = i2 and 0x3F
      out[op++] = map1[o0]
      out[op++] = map1[o1]
      out[op] = if (op < oDataLen) map1[o2] else fixChar
      op++
      out[op] = if (op < oDataLen) map1[o3] else fixChar
      op++
    }
    return out
  }

  /**
   * Decodes a string from Base64Utils format. No blanks or line breaks are allowed
   * within the Base64Utils encoded input data.
   *
   * @param s A Base64Utils String to be decoded.
   * @return A String containing the decoded data.
   * @throws IllegalArgumentException If the input is not valid Base64Utils encoded data.
   */
  fun decodeString(s: String): String {
    return String(decode(s))
  }

  /**
   * Decodes a byte array from Base64Utils format and ignores line separators, tabs
   * and blanks. CR, LF, Tab and Space characters are ignored in the input
   * data. This method is compatible with
   * `sun.misc.BASE64Decoder.decodeBuffer(String)`.
   *
   * @param s A Base64Utils String to be decoded.
   * @return An array containing the decoded data bytes.
   * @throws IllegalArgumentException If the input is not valid Base64Utils encoded data.
   */
  fun decodeLines(s: String): ByteArray {
    val buf = CharArray(s.length)
    var p = 0
    for (ip in 0 until s.length) {
      val c = s[ip]
      if (c != ' ' && c != '\r' && c != '\n' && c != '\t') {
        buf[p++] = c
      }
    }
    return decode(buf, 0, p)
  }

  /**
   * Decodes a byte array from Base64Utils format. No blanks or line breaks are
   * allowed within the Base64Utils encoded input data.
   *
   * @param s A Base64Utils String to be decoded.
   * @return An array containing the decoded data bytes.
   * @throws IllegalArgumentException If the input is not valid Base64Utils encoded data.
   */
  fun decode(s: String): ByteArray {
    return decode(s.toCharArray())
  }

  /**
   * Decodes a byte array from Base64Utils format. No blanks or line breaks are
   * allowed within the Base64Utils encoded input data.
   *
   * @param in A character array containing the Base64Utils encoded data.
   * @return An array containing the decoded data bytes.
   * @throws IllegalArgumentException If the input is not valid Base64Utils encoded data.
   */
  fun decode(data: CharArray): ByteArray {
    return decode(data, 0, data.size)
  }

  /**
   * Decodes a byte array from Base64Utils format. No blanks or line breaks are
   * allowed within the Base64Utils encoded input data.
   *
   * @param in   A character array containing the Base64Utils encoded data.
   * @param iOff Offset of the first character in data to be
   * processed.
   * @param iLen Number of characters to process in data, starting
   * at `iOff`.
   * @return An array containing the decoded data bytes.
   * @throws IllegalArgumentException If the input is not valid Base64Utils encoded data.
   */
  fun decode(data: CharArray, iOff: Int, iLen: Int): ByteArray {
    var iLen = iLen
    if (iLen % 4 != 0) {
      throw IllegalArgumentException(
        "Length of Utils encoded input string is not a multiple of 4."
      )
    }
    while (iLen > 0 && data[iOff + iLen - 1] == fixChar) {
      iLen--
    }
    val oLen = iLen * 3 / 4
    val out = ByteArray(oLen)
    var ip = iOff
    val iEnd = iOff + iLen
    var op = 0
    while (ip < iEnd) {
      val i0 = data[ip++].toInt()
      val i1 = data[ip++].toInt()
      val i2 = (if (ip < iEnd) data[ip++] else 'A').toInt()
      val i3 = (if (ip < iEnd) data[ip++] else 'A').toInt()

      if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
        throw IllegalArgumentException(
          "Illegal character in Utils encoded data."
        )
      }
      val b0 = map2[i0].toInt()
      val b1 = map2[i1].toInt()
      val b2 = map2[i2].toInt()
      val b3 = map2[i3].toInt()
      if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
        throw IllegalArgumentException(
          "Illegal character in Utils encoded data."
        )
      }
      val o0 = (b0 shl 2) or (b1 ushr 4)
      val o1 = ((b1 and 0xf) shl 4) or (b2 ushr 2)
      val o2 = ((b2 and 3) shl 6) or b3
      out[op++] = o0.toByte()
      if (op < oLen) {
        out[op++] = o1.toByte()
      }
      if (op < oLen) {
        out[op++] = o2.toByte()
      }
    }
    return out
  }

  // Dummy constructor.
  private fun Base64Utils() {}
}