package com.simplesys.common.equality

/**
 * By importing `SimpleEquality._` you get '''type-safe''' yet '''unbalanced''' equality:
 *
 * {{{
 * import name.heikoseeberger.demoequality.SimpleEquality._
 *
 * 123 === 666 // false
 * "a" === "a" // true
 * 123 === "a" // won't compile!
 * }}}
 *
 * This equality is unbalanced, i.e. `===` only works (compiles),
 * if the type on the right is a subtype (including the same type) of the type on the left:
 *
 * {{{
 * Seq(1, 2, 3) === List(1, 2, 3)
 * List(1, 2, 3) === Seq(1, 2, 3) // won't compile!
 * }}}
 */
object SimpleEquality {

    implicit class Equal[A](val left: A) extends AnyVal {

        def ===(right: A): Boolean = left == right

        def !==(right: A): Boolean = left != right
    }
}