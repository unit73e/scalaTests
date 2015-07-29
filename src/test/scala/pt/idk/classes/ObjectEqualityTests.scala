package pt.idk.classes

import org.scalatest.Matchers
import org.scalatest.FlatSpec

class EqualsTests extends FlatSpec with Matchers {

  "Two points with the same coordinates" should "be equal" in {
    val p1 = new Point(1, 0)
    val p2 = new Point(1, 0)

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
    val s1 = new Point(1, 0)
    val s2 = s1
    val s3 = new Point(1, 0)

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

}