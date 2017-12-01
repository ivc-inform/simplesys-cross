package com.simplesys.meta

object Utils {
  final class Invalid

  trait Subtype[T1 <: T2, T2]

  trait Equal[T1 >: T2 <: T2, T2]

  class Function1Wrapper[T, R](function: T => R) {
    def apply(a: T) = function(a)
  }

  class Function2Wrapper[T1, T2, R](function: (T1, T2) => R) {
    def apply(a1: T1, a2: T2) = function(a1, a2)
  }

  case class Type2Value[T, V](value: V) {
    def apply() = value
  }

  def to[T, V](implicit function: Type2Value[T, V]) = function()

  def value[T]: T = null.asInstanceOf[T]
}