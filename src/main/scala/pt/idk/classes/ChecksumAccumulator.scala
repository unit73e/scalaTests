package pt.idk.classes

import scala.collection.mutable.WeakHashMap

/**
 * Example of an object.
 * 
 * To do:
 *  - What is an object
 *  - What is a companion object
 *  - What is a companion class
 *  - Differences and similarities between singleton objects and classes
 *  - What is a stand alone object
 */
object ChecksumAccumulator {
  private var cache = WeakHashMap[String, Int]()
  
  def calculate(s: String): Int =
    if (cache.contains(s))
      cache(s)
    else {
      val acc = new ChecksumAccumulator
      for (c <- s)
        acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }
}

/**
 * Example of a class.
 *
 * To do:
 *  - Explain why you can change `sum`
 *  - Explain what a class is
 *  - Explain that parameters in methods are `val` not `var` and why is that
 *  - Explain why `checksum` does not have a `return` statement and why it is
 *    recommended not to have one
 *  - Explain why doesn't `checksum` have curly braces
 *  - Explain why the `add` method has no `=` sign or return type
 */
class ChecksumAccumulator {
  private var sum = 0

  /** Adds a byte to the checksum accumulator. */
  def add(b: Byte) { sum += b }

  /** Returns the checksum. */
  def checksum(): Int = ~(sum & 0xFF) + 1
}