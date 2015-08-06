package pt.idk.classes

class PointMutableEquals(var x: Int, var y: Int) extends Point {

  def move(x: Int, y: Int): Point =
    new PointMutableEquals(this.x + x, this.y + y)

  override def hashCode = 41 * (41 + x) + y

  override def equals(other: Any) = other match {
    case that: PointMutableEquals => this.x == that.x && this.y == that.y
    case _ => false
  }

}