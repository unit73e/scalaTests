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

}