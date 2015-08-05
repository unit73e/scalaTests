package pt.idk.classes

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
class PointBadEquals(val x: Int, val y: Int) extends Point {

  override def move(x: Int, y: Int): Point =
    new PointBadEquals(this.x + x, this.y + y)
  
  /**
   * Compares this object with the given object for equivalence.
   *
   * This method does not override the [[Any.equals]] method on purpose. This
   * is to show one of the common pitfalls when overriding `equals`, namely
   * defining `equals` with the wrong signature.
   */
  def equals(other: PointBadEquals): Boolean =
    x == other.x && y == other.y
  
}