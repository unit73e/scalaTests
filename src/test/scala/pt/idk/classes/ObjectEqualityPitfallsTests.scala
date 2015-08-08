package pt.idk.classes

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import scala.collection.immutable.HashSet

/**
 * A set tests for pitfalls when redefining 'equals' or 'hashCode' methods.
 */
class ObjectEqualityPitfallsTests extends FlatSpec with Matchers {

  "Defining 'equals' with the wrong signature" should
    "return unexpected results" in {
      /*
       * The '==' method should always be used to compare two objects for
       * equality.
       *
       * The 'a == b' expression works as follows:
       *  - If 'a' and 'b' are null, return true
       *  - Otherwise, return the result of 'a equals b'
       *
       * The correct way to enable equality test for any given class is to
       * redefine the inherited 'equals' method. However, one common mistake is
       * to define 'equals' with the wrong signature.
       *
       * Note:
       * The '==' method cannot be redefined. This promotes consistency when
       * comparing null for equality.
       */

      /*
       * Creates two points with the same coordinates.
       */
      val p1 = new PointBadEquals(1, 0)
      val p2 = new PointBadEquals(1, 0)

      /*
       * The 'equals' method was defined so that two points are equal only if
       * both are of type 'Point' and have the same coordinates.
       *
       * Point 'p1' and 'p2' are both of type 'Point' and have the same
       * coordinates, so 'p1 equals p2' should return true. 
       */
      assert(p1 equals p2)

      /*
       * The '==' method returns the result of 'equals' if the caller is not
       * null. Since 'p1' is not null and 'p1 equals p2' returns true,
       * 'p1 == p2' should also return true. Contrary to expected, the
       * expression returns false.
       *
       * More specifically the '==' method depends on the 'equals(Any)' method
       * from 'AnyRef'. Problem is that the 'equals(Any)' method was not
       * overridden but instead a new overloaded alternative 'equals(Point)'
       * was implemented. This means '==' will call the default implementation
       * of 'equals(Any)'.
       *
       * The default implementation of 'equals(Any)' is to compare objects by
       * reference. Since 'p1' and 'p2' reference different objects, the
       * expression returns false.
       */
      assert(!(p1 == p2))

      /*
       * To avoid such mistake always use the 'override' keyword. If the
       * definition of a method included the 'override' keyword and the method
       * is not overriding anything, the code will not compile.
       */
    }

  "Redefining 'equals' but not 'hashCode'" should
    "returns unexpected results when using hash based collections" in {
      /*
       * If two objects are equal, both objects must return the same hash code.
       * A correct 'hashCode' implementation is essential for collections based
       * on hash codes (e.g., HashSet, HashMap) to function properly.
       *
       * If 'equals' is redefined, most likely 'hashCode' must also be
       * redefined. A common mistake is to redefine 'equals' but not 'hashCode'.
       */

      /*
       * Creates two points with the same coordinates.
       */
      val p1 = new PointNoHashCode(1, 0)
      val p2 = new PointNoHashCode(1, 0)

      /*
       * The 'equals' method was redefined so that two points with the same
       * coordinates are equal.
       *
       * Points 'p1' and 'p2' have the same coordinates, so 'p1 == p2' should
       * return true.
       */
      assert(p1 == p2)

      /*
       * Creates an 'HashSet' with a single element 'p1'. 
       */
      val h = HashSet(p1)

      /*
       * As expected 'h' should contain 'p1'.
       */
      assert(h contains p1)

      /*
       * Since 'p1' and 'p2' are equal, its reasonable to think that 'h' should
       * also contain 'p2'. Nonetheless 'h contains p2' will most probably
       * return false. In fact this is not 100% guaranteed.
       *
       * For 'HashSet' to function properly, its elements must have the
       * 'hashCode' method correctly defined. Elements of an 'HashSet' are put
       * into hash buckets determined by their hash code. For efficiency
       * reasons, the 'contains' method does not look into every hash bucket to
       * conclude a given element exists. Instead, the only hash bucket that is
       * looked into is the one the element should belong to. All the other hash
       * buckets are ignored. If the 'hashCode' is incorrectly defined,
       * 'contains' will only look into the wrong hash bucket, ignoring all the
       * others.
       *
       * The 'hasCode' method was not redefined, meaning it will use
       * the default implementation inherited from 'AnyRef'. By default,
       * 'hashCode' returns a transformation of the address of the allocated
       * object. This means 'p1' and 'p2' will almost certainly have different
       * hash codes. Different hash codes are likely to return different hash
       * buckets in a set. So even if 'p1' and 'p2' are equal, they will
       * likely give different hash buckets.
       *
       * The 'h contains p2' expression first determines the bucket that
       * corresponds to 'p2'. Most likely this hash bucket will be different
       * from the bucket 'p1' is in. Point 'p1' will never be found and the
       * expression returns false. Nevertheless, there's a very low chance that
       * 'p1' ends up being in the same bucket as 'p2'. I that case 'p1' will be
       * found and the expression returns true.
       */
      assert(!(h contains p2))

      /*
       * To fix this problem 'hashCode' must be correctly defined. If two
       * objects are equal according to the 'equals' method, calling 'hashCode'
       * on either object must return the same hash code. This means every time
       * 'equals' is redefined, most likely 'hashCode' must also be redefined.
       * Also 'hashCode' may only depend on fields that 'equals' depends on.
       */
    }

  "Definiting 'equals' or 'hashCode' in terms of mutable fields" should
    "return unexpected results when using hash based collections" in {
      /*
       * When mutable objects are used sooner or later unexpected results may
       * occur. One common mistake is to define 'equals' and 'hashCode' based
       * on mutable fields.
       */

      /*
       * Creates a new point.
       */
      val p = new PointMutableEquals(1, 2)

      /*
       * Creates a new 'HashSet' with a single element 'p'.
       */
      val h = HashSet(p)

      /*
       * As expected 'h' should contain 'p'.
       */
      assert(h contains p)

      /*
       * Point 'p' should still exist in 'h' even if one of 'p' coordinates is
       * modified. However, oddly 'h contains p' returns false.
       */
      p.x += 1
      assert(!(h contains p))

      /*
       * Searching every element of 'h' for 'p' returns true, so 'p' does still
       * exist in 'h'.
       *
       * This means, the expression 'h contains p' is not functioning properly.
       */
      assert(h.iterator contains p)

      /*
       * Point 'p' cannot be found because 'contains' expects the elements in
       * the set to have 'equals' and 'hashCode' defined in terms of immutable
       * fields.
       *
       * After 'p' coordinate was modified, the hash code was also modified.
       * Point 'p' is now in an hash bucket that does not correspond to its
       * current hash code. This means 'h contains p' will look into the hash
       * bucket that corresponds to the current hash code, which is not the same
       * 'p' is in. Put in simple terms, 'p' is in the wrong hash bucket
       * according to its current hash code.
       *
       * To avoid such problems, never define 'equals' or 'hashCode' based on
       * mutable fields.
       */
    }
}