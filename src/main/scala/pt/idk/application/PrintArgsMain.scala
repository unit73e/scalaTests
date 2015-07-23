package pt.idk.application

/**
 * Application to print arguments.
 *
 * To run a Scala application the source code must have an object with a `main`
 * method that takes one parameter of type `Array[String]`.
 */
object PrintArgsMain {
  
  /**
   * Prints the arguments.
   * 
   * The `main(Array[String])` method is the entry point of the application.
   */
  def main(args: Array[String]) {
    for (arg <- args)
      println(arg)
  }
  
}