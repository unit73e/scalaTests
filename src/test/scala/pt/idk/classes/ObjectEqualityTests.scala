package pt.idk.classes

import org.scalatest.Matchers
import org.scalatest.FlatSpec
import scala.collection.immutable.HashSet

/**
 * A set of object equality tests.
 */
class ObjectEqualityTests extends FlatSpec with Matchers {

  "Two points with the same coordinates" should "be equal" in {
    /*
     * Creates two points with the same coordinates.
     */
    val p1 = new BasicPoint(1, 0)
    val p2 = new BasicPoint(1, 0)

    /*
     * A point is equal to another if the coordinates are the same. Since 'p1'
     * and 'p2' have the same coordinates they should be equal.
     * 
     * The '==' operator tests equality between two objects. The 'a == b'
     * returns true if either both 'a' and 'b' are null or when 'a equals b'
     * returns true. This means that for '==' to work correctly the 'equals'
     * method must be correctly redefined.
     * 
     * Since 'p1' and 'p2' have the same coordinates, 'p1 == p2' should return
     * true. 
     */
    assert(p1 == p2)
  }

  "The 'eq' and 'ne' operators" should "test reference equality" in {
    /*
     * Creates two points with the same coordinates.
     */
    val p1 = new BasicPoint(1, 0)
    val p2 = new BasicPoint(1, 0)

    /*
     * Creates a new variable 'p3' that references the same object as 'p1'.
     * A reference is an identifier that enables a program to access a
     * particular datum in memory.
     */
    val p3 = p1;

    /* 
     * The 'eq' operator tests reference equality between two objects. The
     * expression 'a eq b' returns true if 'a' and 'b' reference the same
     * object.
     * 
     * Since 'p1' and 'p3' reference the same object, 'p1 eq p3' should return
     * true.
     */
    assert(p1 eq p3)

    /*
     * The 'ne' operator is the opposite of 'eq'. The 'a ne b' expression
     * returns true if 'a' and 'b' do not reference the same object.
     *
     * The variables 'p1' and 'p2' do not reference the same object because
     * the 'new' keyword always allocates memory for a new object.
     * 
     * Since 'p1' and 'p2' do not reference the same object, 'p1 ne p2' should
     * return true.
     * 
     * Note:
     * In theory, since 'Point' is immutable, the compiler or the virtual
     * machine could designed to make 'p1' reference the same object as 'p2'
     * instead of allocating memory for a new object. Storing only one copy of
     * each distinct immutable objects in memory is known as interning. However,
     * Scala only interns some objects (e.g., literal strings) because the cost
     * of testing if an immutable object already exists in memory is high.
     */
    assert(p1 ne p2)
  }

}
