package pt.idk.classes

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import scala.collection.immutable.HashSet

/**
 * A set tests for pitfalls when redefining 'equals' or 'hashCode' methods. 
 */
class ObjectEqualityPitfallsTests extends FlatSpec with Matchers {

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

  "Redefining 'equals' but not 'hashCode'" should
    "returns unexpected results when using hash based collections" in {
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

  "Definiting 'equals' or 'hashCode' based on mutable fields" should
    "return unexpected results when using hash based collections" in {
      /*
       * Creates a new point. This particular implementation of point defines
       * 'equals' and 'hashCode' in terms of mutable fields.
       */
      val p = new PointMutableEquals(1, 2)

      /*
       * Creates a new hash set with 'p' as its only element.
       */
      val h = HashSet(p)

      /*
       * As expected 'h' should contain 'p'.
       */
      assert(h contains p)

      /*
       * Point 'p' should be contained in 'h' even one of its coordinates is
       * modified. However, 'h contains p' returns false after changing one of
       * 'p' coordinates.
       */
      p.x += 1
      assert(!(h contains p))

      /*
       * Point 'p' still exists in 'h' but cannot be found using 'contains'
       * test. One way to prove this is by comparing each element of 'h' with
       * 'p'.
       */
      assert(h.iterator contains p)

      /*
       * The problem is that by changing one of 'p' coordinates, the hash code
       * will not be the same anymore.
       * 
       * The hash code of 'p' is calculated in terms of its coordinates. If the
       * coordinates as modified, so is the hash code. Problem is that 'p' was
       * put into the hash set based on one hash code and is being searched
       * with another hash code.
       * 
       * Never redefine 'equals' or 'hashCode' based on mutable objects.
       */
    }
}