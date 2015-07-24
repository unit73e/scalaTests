# Scala Tests

This project contains some Scala tests. The objective is to understand Scala as
quickly as possible.

This project uses SBT. To create Eclipse project files run:

    $ sbt eclipse

Then you can import the project in eclipse:

    File -> Import -> Existing Project in Workspace

## Tests

The tests collections are organized in packages. For example, the application
entry point tests are in the `pt.idk.application` package. The next section
give further details of each collection of tests. 

## Application

The application tests show how to program the entry point of a Scala
application. Because the tests are entry points, they can be executed in the
terminal. One way to execute the these tests is to use SBT:

     $ sbt "run hello world"
     
Since there are multiple entry points, you will be asked which one you want to
run. Just choose one and the test will be executed. 

## TODO

These should be done in the future but doesn't make sense to include in code.
 - Explain semicolon inference.
 - Difference between application and script.
