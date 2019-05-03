package com.hviewtech.wawupay.common.utils

import java.math.BigDecimal

/**
 * @author su
 * @date 2018/4/14
 * @description
 */

object NumberUtils {

  private fun withScale(decimal: BigDecimal): BigDecimal {
    return decimal.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
  }

  fun valueOf(value: String?): BigDecimal {
    return withScale(BigDecimal(value ?: "0"))
  }

  fun valueOf(value: Int): BigDecimal {
    return withScale(BigDecimal.valueOf(value.toLong()))
  }

  fun valueOf(value: Float): BigDecimal {
    return withScale(BigDecimal.valueOf(value.toDouble()))
  }

  fun valueOf(value: Double): BigDecimal {
    return withScale(BigDecimal.valueOf(value))
  }

  fun valueOf(value: Long): BigDecimal {
    return withScale(BigDecimal.valueOf(value))
  }

  fun valueOf(value: Number): BigDecimal {
    return withScale(BigDecimal.valueOf(value.toDouble()))
  }

  fun divide(dividend: BigDecimal?, divisor: BigDecimal): BigDecimal {
    return if (dividend == null) {
      BigDecimal.ZERO
    } else dividend.divide(divisor, 2, BigDecimal.ROUND_HALF_UP)
  }

  fun divide(dividend: BigDecimal?, divisor: Int): BigDecimal {
    return if (dividend == null) {
      BigDecimal.ZERO
    } else withScale(divide(dividend, valueOf(divisor)))
  }

  fun divide100(dividend: BigDecimal?): String {
    return if (dividend == null) {
      ""
    } else toPlainString(withScale(divide(dividend, valueOf(100))))
  }

  fun multiply(multiplier: BigDecimal?, multiplicand: BigDecimal): BigDecimal {
    return if (multiplier == null) {
      BigDecimal.ZERO
    } else withScale(multiplier.multiply(multiplicand))
  }

  fun multiply(multiplier: BigDecimal?, multiplicand: Int): BigDecimal {
    return if (multiplier == null) {
      BigDecimal.ZERO
    } else withScale(multiplier.multiply(valueOf(multiplicand)))
  }

  fun multiply100(multiplier: BigDecimal?): String {
    return if (multiplier == null) {
      ""
    } else toPlainString(multiply(multiplier, valueOf(100)))
  }

  fun add(addend: BigDecimal?, augend: BigDecimal): BigDecimal {
    return if (addend == null) {
      BigDecimal.ZERO
    } else withScale(addend.add(augend))
  }

  fun subtract(subtraction: BigDecimal?, subtrahend: BigDecimal): BigDecimal {
    return if (subtraction == null) {
      BigDecimal.ZERO
    } else withScale(subtraction.subtract(subtrahend))
  }

  fun toPlainString(value: BigDecimal?): String {
    return if (value == null) "0" else value.stripTrailingZeros().toPlainString()
  }

}
