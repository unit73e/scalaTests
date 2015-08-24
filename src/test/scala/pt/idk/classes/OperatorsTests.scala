package pt.idk.classes

import scala.language.postfixOps

import org.scalatest.FlatSpec
import org.scalatest.Matchers

/**
 * A set of operator tests.
 *
 * The objective of these tests is to understand how operators work in Scala.
 *
 * == General information ==
 *
 * In computer programming, operators are language constructs that represent the
 * function of an operation. An operation is an action that produces a result
 * from zero or more arguments, called operands. For example,  in the '3 + 2'
 * expression, the operation is called addition, the '+' symbol is the operator,
 * 3 and 2 are the operands and the result is 5.
 *
 * The number of operands that an operation accepts is called arity. The most
 * common way to refer to the arity of function is to combine a numeral prefix
 * with the '-ary' suffix:
 *
 *  - Nullary: operation with zero operands (e.g., 'Pi', 'e')
 *  - Unary: operation with one operand (e.g., '-3', '+5')
 *  - Binary: operation with two operands (e.g., '3 + 2', '4 * 5')
 *  - Trenary: operation with three operands (e.g., 'a ? true : false')
 *  - N-ary: operation with `n` operands
 *
 * The operator notation is the position of the operator in relation to its
 * operands. There are three operator notations:
 *
 *  - Infix notation: the operators are placed between the operands
 *    (e.g., `3 + 2`, `4 * 5`, `1 / 4`)
 *  - Prefix notation: the operators are placed to the left of the operands
 *    (e.g., `+ 3 2`, `-3`, `!true`)
 *  - Post-fix notation: the operators are placed to the right of the operands
 *    (e.g., `3 2 +`, `4 1 *`)
 *
 * In most programming languages operators behave very similarly to functions or
 * methods but differ syntactically and semantically. A function may be seen as
 * a prefix operator and a method as an infix operator, but both with slightly
 * different syntax and fixed precedence and associativity rules.
 *
 * == Operators are methods ==
 *
 * In Scala operator notation is just nice syntax for method calls. Any
 * expression written in operator notation is transformed into correspondent
 * method calls by the compiler.
 *
 * Scala supports operator infix notation for any method with one or more
 * arguments. Any expression `a <op> (n, ..., m)` gets transformed into
 * `a.<op>(b, ..., d)`. Here are some examples:
 *
 *  - `3 + 2` gets transformed into `3.+(2)`
 *  - `"test" contains 'e'` gets transformed into `"test".contains('e')`
 *  - `"test" indexOf ('s', 1)` gets transformed into `"test".indexOf('s', 1)`
 *
 * This means a method name can be composed of symbols, for example, 'Int' has a
 * method '+(Int):Int'. By convention, only methods that do not have
 * side-effects should be written in infix notation.
 *
 * Scala also supports unary prefix operator notation. The only identifiers that
 * can be used as prefix operators are `+`, `-`, `!` and `~`. So while
 * `!true` and `-3` are valid, `*3` is not. Any prefix notation expression
 * `<op>a` is transformed into `a.unary_<op>`. Some examples follow:
 *
 *  - `!true` gets transformed into `true.unary_!`
 *  - `-3.0` gets transformed into `(3.0).unary_-`
 *
 * Finally, Scala also supports unary post-fix operator notation. Naturally only
 * methods without arguments can be used in post-fix notation. Any post-fix
 * expression `a <op>` gets transformed into `a.<op>`. For example:
 *
 *  - `"test" toUpper` gets transformed into `"test".toUpper()`
 *
 * One problem with post-fix notation is that, due to semicolons being optional,
 * expressions will first be treated in infix notation, potentially taking a
 * term from the next line. For that reason, post-fix notation is strongly
 * discouraged.
 */
class OperatorsTests extends FlatSpec with Matchers {

  "Operators" should "be methods" in {
    /*
     * If operators are methods, any operator notation expression should have an
     * equivalent method call expression. In fact, the compiler transforms
     * operator notation expressions into equivalent method call expressions.
     */

    /*
     * Any binary infix operator 'a <op> b' gets transformed into 'a.<op>(b)'
     */

    val a = 1 + 2
    val b = 1.+(2)
    assert(a == b)

    /*
     * Naturally this means 'Int' has a method '+(Int):Int'. In fact, there are
     * other '+' overloaded methods, such as, '+(Long):Long'. 
     */

    val c: Long = 1 + 2L
    val d: Long = 1.+(2L)
    assert(c == d)

    /*
     * Any method can be written in infix notation, not just mathematical
     * symbols, such as '+'.
     */

    val x = "test" contains 'e'
    val y = "test".contains('e')
    assert(x == y)

    /*
     * More generically, any n-ary infix operator 'a <op> (n, ..., m)' gets
     * transformed into 'a.<op>(n, ..., m).
     */

    val w = "test" indexOf ('e', 1)
    val z = "test".indexOf('e', 1)
    assert(w == z)

    /*
     * Prefix notation expressions '<op>a' get transformed into 'a.unary_<op>'.
     */

    val q = !true
    val p = true.unary_!
    assert(q == p)

    /*
     * Post-fix notation expressions 'a <op>' get transformed into 'a.<op>'.
     */

    val h = "test" toUpperCase
    val j = "test".toUpperCase()
    assert(h == j)
  }

}