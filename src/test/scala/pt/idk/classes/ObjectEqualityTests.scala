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
 * This class does not override `hashCode` on purpose. This 
 */
class PointNoHashCode(val x: Int, val y: Int) {
  override def equals(other: Any): Boolean = other match {
    case that: Point => x == that.x && y == that.y
    case _ => false
  }
}