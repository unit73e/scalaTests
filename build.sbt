lazy val root = (project in file(".")).
  settings(
    name := "TestScala",
    version := "1.0.0",
    scalaVersion := "2.11.7"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % Test
