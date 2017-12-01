package com.simplesys.common.equality

import scala.annotation.implicitNotFound

/**
 * By importing `ViewWiseBalancedEquality._` you get '''type-safe''' and '''view-wise balanced''' equality:
 *
 * {{{
 * import name.heikoseeberger.demoequality.ViewWiseBalancedEquality._
 *
 * 123 === 666 // false
 * "a" === "a" // true
 * 123 === "a" // won't compile!
 * }}}
 *
 * This equality is balanced with respect to implicit conversions,
 * hence `===` works (compiles) for any two arguments on the left and right,
 * whose types are in a implicit conversion relationship (which includes a subtype relationship),
 * i.e. one can be viewed as the other:
 *
 * {{{
 * Seq(1, 2, 3) === List(1, 2, 3)
 * List(1, 2, 3) === Seq(1, 2, 3)
 * 1L === 1
 * 1 === 1L
 * }}}
 */
object ViewWiseBalancedEquality {

  /**
   * Extends any type with a type-safe and balanced `===` operator.
   */
  implicit class Equal[L](val left: L) extends AnyVal {

    /**
     * Type-safe and balanced `===` operator.
     */
    def ===[R](right: R)(implicit equality: Equality[L, R]): Boolean =
      equality.areEqual(left, right)
  }

  /**
   * Type-class representing equality of two arguments with arbitrary respective types.
   */
  @implicitNotFound("ViewWiseBalancedEquality requires ${L} and ${R} to be in an implicit conversion relationship, i.e. one can be viewed as the other!")
  sealed trait Equality[L, R] {

    /**
     * `true` if the two given arguments are equal, otherwise `false`.
     */
    def areEqual(left: L, right: R): Boolean
  }

  /**
   * Provides type-class instances based on natural equality for any two types
   * in a `L => R` or `R => L` implicit conversion relationship.
   */
  object Equality extends LowPriorityEqualityImplicits {

    /**
     * Provides a type-class instance based on natural equality for any two types
     * in a `R => L` implicit conversion relationship.
     */
    implicit def rightToLeftEquality[L, R](implicit view: R => L): Equality[L, R] =
      new RightToLeftViewEquality(view)
  }

  /**
   * Mixed into [[[Equality$ Equality]]] to disambiguate the `L =:= R` case.
   */
  trait LowPriorityEqualityImplicits {

    /**
     * Provides a type-class instance based on natural equality for any two types
     * in a `L => R` implicit conversion relationship.
     */
    implicit def leftToRightEquality[L, R](implicit view: L => R): Equality[L, R] =
      new LeftToRightViewEquality(view)
  }

  private class LeftToRightViewEquality[L, R](view: L => R) extends Equality[L, R] {

    override def areEqual(left: L, right: R): Boolean =
      view(left) == right
  }

  private class RightToLeftViewEquality[L, R](view: R => L) extends Equality[L, R] {

    override def areEqual(left: L, right: R): Boolean =
      left == view(right)
  }
}