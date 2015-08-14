package pt.idk.utils

object IEEE754Utils {

  /**
   * Minimum IEEE 754 single precision value
   *
   * The minimum value should be: −((2 − 2⁻²³) × 2¹²⁷)
   */
  def minFloat(): Float = -maxFloat()

  /**
   * Maximum IEEE 754 single precision value
   *
   * The maximum value should be: (2 − 2⁻²³) × 2¹²⁷
   */
  def maxFloat(): Float = {
    val max = (2 - Math.pow(2, -23)) * Math.pow(2, 127)
    max.toFloat
  }

  /**
   * Maximum IEEE 754 double precision value.
   *
   * The maximum value should be: (1 + (1 − 2⁻⁵²)) × 2¹⁰²³
   */
  def maxDouble(): Double = (1 + (1 - Math.pow(2, -52))) * Math.pow(2, 1023)

  /**
   * Minimum IEEE 754 double precision value
   *
   * The minimum value should be: −((1 + (1 − 2⁻⁵²)) × 2¹⁰²³)
   */
  def minDouble(): Double = -maxDouble()

}