package com.simplesys.common.equality

import scala.annotation.implicitNotFound

/**
 * By importing `TypeWiseBalancedEquality._` you get '''type-safe''' and '''type-wise balanced''' equality:
 *
 * {{{
 * import name.heikoseeberger.demoequality.TypeWiseBalancedEquality._
 *
 * 123 === 666 // false
 * "a" === "a" // true
 * 123 === "a" // won't compile!
 * }}}
 *
 * This equality is balanced with respect to subtyping,
 * hence `===` works (compiles) for any two arguments on the left and right,
 * whose types are in a subtype relationship (which includes type equality):
 *
 * {{{
 * Seq(1, 2, 3) === List(1, 2, 3)
 * List(1, 2, 3) === Seq(1, 2, 3)
 * }}}
 *
 * Yet it doesn't work (compile) for two arguments on the left and right,
 * whose types are in an implicit conversion relationship:
 *
 * {{{
 * 1L === 1 // won't compile!
 * 1 === 1L // won't compile!
 * }}}
 */
object TypeWiseBalancedEquality {

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
    @implicitNotFound("TypeWiseBalancedEquality requires ${L} and ${R} to be in a subtype relationship!")
    sealed trait Equality[L, R] {

        /**
         * `true` if the two given arguments are equal, otherwise `false`.
         */
        def areEqual(left: L, right: R): Boolean
    }

    /**
     * Provides type-class instances based on natural equality for any two types
     * in a `L <: R` or `R <: L` relationship.
     */
    object Equality extends LowPriorityEqualityImplicits {

        /**
         * Provides a type-class instance based on natural equality for any two types
         * in a `R <: L` relationship.
         */
        implicit def rightSubtypeOfLeftEquality[L, R <: L]: Equality[L, R] =
            AnyEquality.asInstanceOf[Equality[L, R]]
    }

    trait LowPriorityEqualityImplicits {

        /**
         * Provides a type-class instance based on natural equality for any two types
         * in a `L <: R` relationship.
         */
        implicit def leftSubtypeOfRightEquality[R, L <: R]: Equality[L, R] =
            AnyEquality.asInstanceOf[Equality[L, R]]
    }

    private object AnyEquality extends Equality[Any, Any] {

        override def areEqual(left: Any, right: Any): Boolean =
            left == right
    }
}