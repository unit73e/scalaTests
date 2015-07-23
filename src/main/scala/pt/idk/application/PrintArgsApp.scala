package pt.idk.application

/**
 * Application to print arguments.
 * 
 * Another way to start an application is to extends [[App]].
 */
object PrintArgsApp extends App {
  for (arg <- args)
    println(arg)
}