package pt.idk.classes

import org.scalatest.Matchers
import org.scalatest.FlatSpec
import scala.collection.immutable.HashSet

class EqualsTests extends FlatSpec with Matchers {

  /*
   * = Object Equality =
   * 
   * The following tests show how are objects compared in Scala, assuming the
   * 'equals' method is correctly implemented.
   */

  "Two points with the same coordinates" should "be equal" in {
    val p1 = Point(1, 0)
    val p2 = Point(1, 0)

    /*
     * The '==' operator compares the value of two objects.
     *
     * The 'a == b' expression returns true either when 'a' and 'b' are both
     * null, or when 'a equals b' evaluates to true. Assuming the 'equals'
     * method is correctly implemented, the following assertion should return
     * true because the points 'p1' and 'p2' have the same coordinates.
     *
     * Note that the '==' operator in Java has a different behavior than Scala.
     * In Java, when objects are compared, the '==' operator tests reference
     * equality instead of value equality. The following assertion would fail in
     * Java because 's1' and 's2' do not reference the same object.
     */
    assert(p1 == p2)
  }

  "The 'eq' and 'ne' operators" should "test reference equality" in {
    val s1 = Point(1, 0)
    val s2 = s1
    val s3 = Point(1, 0)

    /*
     * The 'a eq b' returns true if 'a' and 'b' reference the same object.
     *
     * The variable 's2' should reference the same object as 's1' because 's2'
     * was defined to be equal to 's1'.
     */
    assert(s1 eq s2)

    /*
     * The 'a ne b' returns true if 'a' and 'b' do not reference the same
     * object.
     *
     * The variables 's1' and 's3' should not reference the same object because
     * 'new' should always allocate memory for a new object.
     *
     * Theoretically, since 'Point' is immutable, it would make sense for 's3'
     * to just reference the same value as 's1' instead of allocating more
     * memory. However this has a cost and the JVM has such behavior for some
     * objects, such as, literal strings (known as string interning).
     */
    assert(s1 ne s3)
  }

  /*
   * = Override equals pitfalls = 
   * 
   * The following tests show the pitfalls of implementing the 'equals' method.
   */

  "Defining 'equals' with the wrong signature" should "not override the " +
    "'Any.equals' method, giving unexpected results" in {
      val a = new PointBadEquals(1, 0)
      val b = new PointBadEquals(1, 0)

      /*
       * The 'equals' operator will call 'equals(o: PointBadEquals)' method
       * because both 'a' and 'b' are 'PointBadEquals'.
       */
      assert(a equals b)

      /*
       * The '==' method will call the inherited 'equals(o: Any)' method, not
       * the 'equals(o: PointBadEquals)' method.
       * 
       * The default implementation of [[Any.equals]] is to compare objects by
       * reference. Since 'a' and 'b' reference different objects, the
       * expression will return 'false'.
       */
      assert(!(a == b))

      /*
       * Always use the 'override' keyword to avoid this pitfall. If a method
       * is not being overridden, the code will not compile.
       */
    }

  "Defining 'equals' but not 'hashCode'" should "returns unexpected results " +
    "when using hash based collections" in {
      /*
       * Creates two points with the same coordinates.
       */
      // TODO: Create Point trait
      val p1 = new PointNoHashCode(1, 0)
      val p2 = new PointNoHashCode(1, 0)

      /*
       * The 'equals' method was redefined so that two points with the same
       * coordinates are equal. This means points 'p1' and 'p2' should be equal
       * because they have the same coordinates.
       * 
       */
      assert(p1 == p2)

      /*
       * Creates an 'HashSet' with a single element 'p1'.
       */
      val h = HashSet(p1)

      /*
       * As expected, 'h' should contain 'p1'.
       */
      assert(h contains p1)

      /*
       * Since 'p1' and 'p2' are equal, it is reasonable to think 'h' should
       * also contain 'p2'. Nonetheless 'h contains p2' will most probably
       * return false. In fact this is not 100% guaranteed.
       * 
       * This unexpected result should be made clear if the 'contains'
       * implementation of 'HashSet' is known. Elements of an 'HashSet' are put
       * into hash buckets based on their hash codes. The 'contains' test first
       * determines the hash bucket to look into and then checks if the given
       * element is in the hash bucket. The problem is that if the element being
       * searched returns a wrong hash code, the 'contains' method will look
       * into the wrong hash bucket.
       * 
       * The Point 'hasCode' method was not redefined, meaning it will use
       * the default implementation inherited from 'AnyRef'. By default,
       * 'hashCode' returns a transformation of the address of the allocated
       * object. This means 'p1' and 'p2' will almost certainly have different
       * hash codes. Different hash codes have a high probability of different
       * hash buckets in a set. So even if 'p1' and 'p2' are equal, they will
       * likely give different hash buckets in a set.
       * 
       * In summary, what happens when 'h contains p2' is executed is the
       * following. First, the bucket that corresponds to 'p2' hash code is
       * looked into. Most likely 'p1' is is another bucket, so it will never
       * be found, returning false. Still, there's a low chance that 'p1' ends
       * up being in the same bucket as 'p2' and in that case it will be found,
       * returning true.
       * 
       * To fix this problem 'hashCode' must be defined as described in its
       * contract:
       * 
       *   If two objects are equal according to the equals method, then
       *   calling the hashCode method on each of the two objects must
       *   produce the same integer result.
       * 
       * Every time 'equals' is redefined 'hashCode' must also be redefined so
       * that the contract is not breached.
       */
      assert(!(h contains p2))
    }
}

/**
 * A two dimensional point.
 *
 * The definition of the `equals` method is purposely defined with the wrong
 * signature, meaning that the inherited [[Any.equals]] method has not been
 * overridden. One consequence of this is that the [[Any.==]] method will
 * compare objects with the default implementation, that is, the objects will
 * be compared by reference, not value.
 *
 * One way to avoid this pitfall is to always use the `override` keyword. If
 * the `override` keyword is used and nothing is being overridden, the code will
 * not compile.
 */
class PointBadEquals(val x: Int, val y: Int) {

  /**
   * Compares this object with the given object for equivalence.
   *
   * This method does not override the [[Any.equals]] method on purpose. This
   * is to show one of the common pitfalls when overriding `equals`, namely
   * defining `equals` with the wrong signature.
   */
  def equals(other: PointBadEquals): Boolean = x == other.x && y == other.y
}

/**
 * A two dimensional point.
 *
 * This class does not override `hashCode` on purpose.
 */
class PointNoHashCode(val x: Int, val y: Int) {
  override def equals(other: Any): Boolean = other match {
    case that: PointNoHashCode => x == that.x && y == that.y
    case _ => false
  }
}