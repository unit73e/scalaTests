package pt.idk.classes

class BasicPoint(val x: Int, val y: Int) extends Point with Equals {

  override def move(x: Int, y: Int): Point =
    new BasicPoint(this.x + x, this.y + y)

  override def toString(): String = "(" + x + ", " + y + ")"

  def canEqual(other: Any) = {
    other.isInstanceOf[BasicPoint]
  }

  override def equals(other: Any) = {
    other match {
      case that: BasicPoint => that.canEqual(BasicPoint.this) &&
        x == that.x && y == that.y
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime + x.hashCode) + y.hashCode
  }

}