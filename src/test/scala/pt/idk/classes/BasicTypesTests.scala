package pt.idk.classes

import org.scalatest.FlatSpec
import pt.idk.utils.IEEE754Utils

class BasicTypesTests extends FlatSpec {

  "A Byte" should "have a range between -2⁷ and 2⁷-1 inclusive" in {
    val minByte: Byte = (-Math.pow(2, 7)).toByte
    val maxByte: Byte = (Math.pow(2, 7) - 1).toByte

    assert(Byte.MinValue == minByte)
    assert(Byte.MaxValue == maxByte)
  }

  "A Short" should "have a range between -2¹⁵ and 2¹⁵-1 inclusive" in {
    val minShort: Short = (-Math.pow(2, 15)).toShort
    val maxShort: Short = (Math.pow(2, 15) - 1).toShort

    assert(Short.MinValue == minShort)
    assert(Short.MaxValue == maxShort)
  }

  "An Int" should "have a range between -2³¹ and 2³¹-1 inclusive" in {
    val minInt: Int = (-Math.pow(2, 31)).toInt
    val maxInt: Int = (Math.pow(2, 31) - 1).toInt

    assert(Int.MinValue == minInt)
    assert(Int.MaxValue == maxInt)
  }

  "A Long" should "have a range between -2⁶³ and 2⁶³-1 inclusive" in {
    val minLong: Long = (-Math.pow(2, 63)).toLong
    val maxLong: Long = (Math.pow(2, 63) - 1).toLong

    assert(Long.MinValue == minLong)
    assert(Long.MaxValue == maxLong)
  }

  "A Char" should "be a 16 bit unsigned Unicode character" in {
    val minChar: Char = 0
    val maxChar: Char = 0xFFFF

    assert(Char.MinValue == minChar)
    assert(Char.MaxValue == maxChar)
  }

  "A String" should "be a sequence of Char" in {
    val s: String = "test";

    /*
     * This should iterate every character of string 's' and check if each is
     * of type Char.
     */
    for (c <- s)
      assert(c.isInstanceOf[Char])
  }

  "A Float" should "be a 32-bit IEEE 754 single-precision float" in {
    assert(Float.MinValue == IEEE754Utils.minFloat())
    assert(Float.MaxValue == IEEE754Utils.maxFloat())
  }

  "A Double" should "be a 64-bit IEEE 754 double-precision float" in {
    assert(Double.MinValue == IEEE754Utils.minDouble())
    assert(Double.MaxValue == IEEE754Utils.maxDouble())
  }

  "A Boolean" should "be 'true' or 'false'" in {
    assert(true.isInstanceOf[Boolean])
    assert(false.isInstanceOf[Boolean])
  }

}