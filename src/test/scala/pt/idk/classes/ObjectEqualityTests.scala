package pt.idk.classes

import org.scalatest.Matchers
import org.scalatest.FlatSpec

class EqualsTests extends FlatSpec with Matchers {
  
  "Two points with the same coordinates" should "be equal" in {
    val p1 = new Point(1,0);
    val p2 = new Point(1,0);
    p1 should equal (p2);
  }
}