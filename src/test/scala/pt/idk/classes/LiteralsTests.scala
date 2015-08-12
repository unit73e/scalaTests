package pt.idk.classes

import org.scalatest.FlatSpec

class LiteralsTests extends FlatSpec {

  /*
   * Integer literal tests
   */

  "A decimal integer literal (base 10)" should "be composed only of digits" in {
    // An integer literal must be of type Byte, Short, Int or Long
    assert(120.isInstanceOf[Int])
    assert(255.isInstanceOf[Int])
  }

  "An hexadecimal integer literal (base 16)" should "begin with 0x or 0X " +
    "followed by digits or characters from A to F" in {
      assert(0xABAB.isInstanceOf[Int])

      /*
       * In an hexadecimal literal both lower and upper case characters should
       * be valid
       */
      assert(0xf1a2.isInstanceOf[Int])
      assert(0XAbAb.isInstanceOf[Int])

      /*
       * Comparing an hexadecimal to a decimal should be possible because both
       * are valid integer literals.
       */
      assert(0xFFFF == 65535)
    }

  "By default an integer literal" should "be an Int" in {
    assert(120.isInstanceOf[Int])
    assert(0xFFFF.isInstanceOf[Int])
  }

  "An integer literal that ends with L or l" should "be a Long" in {
    assert(120l.isInstanceOf[Long])
    assert(0xFa12L.isInstanceOf[Long])
  }

  "An integer literal assigned to a variable of type Short or Byte" should
    "be treated as if it were of that type" in {
      var a: Byte = 80
      var b: Short = 255
      assert(a.isInstanceOf[Byte])
      assert(b.isInstanceOf[Short])

      // If its treated as a Byte or Short it cannot be treated as an Int
      assert(!a.isInstanceOf[Int])
      assert(!b.isInstanceOf[Int])
    }

  /*
   * Floating point literal tests
   */

  "A floating point literal" should "consist of decimal digits between one " +
    "point, followed by an optional E or e and an exponent" in {
      // A floating point literal must be of type Float or Double
      assert(1.2.isInstanceOf[Double])
      assert(1.23e10.isInstanceOf[Double])
      assert(.2E10.isInstanceOf[Double])
    }

  "The exponential portion of a floating point literal" should "mean the " +
    "power of 10 by which the other portion is multiplied (e.g., 2e5 should " +
    "be equivalent to 2×10⁵)" in {
      assert(2e1 == 20)
      assert(22.2101E3 == 22210.1)
    }

  "By default a floating point literal" should "be a Double, which can be " +
    "enforced by placing a D or d at the end" in {
      assert(1.2.isInstanceOf[Double])
      assert(4.0e10d.isInstanceOf[Double])

      /*
       * The literal 4 alone should be an Int but with a D at the end should be
       * a Double instead.
       */
      assert(4D.isInstanceOf[Double])
    }

  "A floating point that ends with an F or f" should "be a Float" in {
    assert(1.2f.isInstanceOf[Float])
    assert(1.2e10F.isInstanceOf[Float])
  }

  /*
   * Character literal tests
   */

  "A character literal" should "be a unicode character between " +
    "single quotes" in {
      // A character literal must be an instance of Char
      assert('a'.isInstanceOf[Char])
      assert('Z'.isInstanceOf[Char])
    }

  "A character literal" can "also be a code point in hexadecimal form, " +
    "preceded by \\u, between single quotes" in {
      assert('\u0030'.isInstanceOf[Char])

      /*
       * Comparing an explicit character to a code point should be possible
       * because both are valid character literals.
       */
      assert('\u0041' == 'A')
    }

  "A unicode code point" can "appear anywhere in the source code" in {
    /*
     * Code point to character mapping should be:
     *  - U+0073 = 's'
     *  - U+0074 = 't'
     *  
     * So 'te\u0073\u0074' is the same as 'test'
     */
    val te\u0073\u0074 = "test"
    assert(test == "test")

    /*
     * Explicit non-ASCII characters can also be used
     */
    val Џ = "Џ"
    assert(\u040F == "Џ")

    /*
     * Obviously it is a bad idea to name identifiers like this. The objective
     * of this syntax is to allow source files that include non-ASCII Unicode
     * characters to be represented in ASCII.
     */
  }

  "Some characters" can "be represented in special escape sequences" in {
    val linefeed = '\n'
    val backspace = '\b'
    val tab = '\t'
    val formfeed = '\f'
    val carriageReturn = '\r'
    val doubleQuote = '\"'
    val singleQuote = '\''
    val backslash = '\\'

    assert(linefeed == '\u000A')
    assert(backspace == '\u0008')
    assert(tab == '\u0009')
    assert(formfeed == '\u000C')
    assert(carriageReturn == '\u000D')
    assert(doubleQuote == '\u0022')
    assert(singleQuote == '\u0027')

    // Even the code point must be escaped
    assert(backslash == '\u005C\u005C')
  }

  /*
   * String literal tests
   */

  "A string literal" should "be a sequence of characters surrounded by " +
    "double quotes" in {
      // A string literal must be of type String
      assert("test".isInstanceOf[String])
    }

  "The syntax of characters in a string literal" should "be the same as " +
    "as with character literals" in {
      // Any valid character should be able to be part of a string
      assert("\\Џ\u0041".isInstanceOf[String])

      /*
       * Comparing code points with explicit characters should be valid since
       * both are literal characters
       */
      assert("★☯€" == "\u2605\u262F\u20AC")
    }

  "A raw literal string" should "be a sequence of characters surrounded " +
    "by three double quotation marks (\"\"\")" in {

      // A raw literal string should not escape any characters
      assert("""\\\n""" == "\\\\\\n")

      /*
       * Characters in a raw literal string should be processed strictly as
       * typed. Note that the value of 's1' includes a line feed character
       * ('\n') and blank characters (' ').
       */
      val s1 = """Hello,
                  World"""
      val s2 = "Hello,\n                  World"
      assert(s1 == s2)

      /*
       * Note:
       * The 'stripMargin' method can be used to remove the perhaps undesired
       * blank characters.
       */
      val s3 = """|Hello,
                  |World""".stripMargin
      val s4 = "Hello,\nWorld"
      assert(s1 == s2)
    }

  /*
   * Symbol literal tests
   */

  "A symbol literal" should "be any alphanumeric identifier preceded by a " +
    "single quote" in {
      // A symbol literal must be of type Symbol
      assert('test.isInstanceOf[Symbol])
    }

  "Two equal symbols" should "always reference the same object in " +
    "memory (in other words, symbols are always interned)" in {
      assert('test eq 'test)
      assert(Symbol("test") eq 'test)
    }

  /*
   * Boolean literal tests
   */

  "A boolean literal" should "be either 'true' or 'false'" in {
    // A boolean literal must of type Boolean
    assert(true.isInstanceOf[Boolean])
    assert(false.isInstanceOf[Boolean])
  }
}