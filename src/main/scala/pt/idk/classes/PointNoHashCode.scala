package pt.idk.classes;

/**
 * A two dimensional point.
 *
 * This class does not override `hashCode` on purpose.
 */
class PointNoHashCode(val x: Int, val y: Int) extends Point {
  
  override def move(x: Int, y: Int): Point =
    new PointNoHashCode(this.x + x, this.y + y) 
  
  override def equals(other: Any): Boolean = other match {
    case that: PointNoHashCode => x == that.x && y == that.y
    case _ => false
  }
  
}