package pt.idk.classes

class Point(val x: Int, val y: Int) extends Equals {
  
  def move(x: Int, y: Int): Point = new Point(this.x + x, this.y + y)
  
  override def toString(): String = "(" + x + ", " + y + ")"

  def canEqual(other: Any) = {
    other.isInstanceOf[Point]
  }

  override def equals(other: Any) = {
    other match {
      case that: Point => that.canEqual(Point.this) &&
        x == that.x && y == that.y
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime + x.hashCode) + y.hashCode
  }

}