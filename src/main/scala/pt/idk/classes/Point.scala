package pt.idk.classes

trait Point {

  def x: Int

  def y: Int

  def move(x: Int, y: Int): Point
}