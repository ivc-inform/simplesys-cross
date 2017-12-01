package com.simplesys.meta

import com.simplesys.meta.Utils.Type2Value

object Booleans {

  sealed trait TBool {
    type And[T <: TBool] <: TBool
    type Or[T <: TBool] <: TBool
    type Not <: TBool
    type If[IfTrue, ifFalse]
    type If2[T, IfTrue <: T, IfFalse <: T] <: T
  }

  final class TTrue extends TBool {
    type And[T <: TBool] = T
    type Or[T <: TBool] = TTrue
    type Not = TFalse
    type If[IfTrue, IfFalse] = IfTrue
    type If2[T, IfTrue <: T, IfFalse <: T] = IfTrue
  }

  val True = new TTrue

  final class TFalse extends TBool {
    type And[T <: TBool] = TFalse
    type Or[T <: TBool] = T
    type Not = TTrue
    type If[IfTrue, IfFalse] = IfFalse
    type If2[T, IfTrue <: T, IfFalse <: T] = IfFalse
  }

  val False = new TFalse

  type &&[T1 <: TBool, T2 <: TBool] = T1#And[T2]
  type ||[T1 <: TBool, T2 <: TBool] = T1#Or[T2]

  implicit val tFalse2Boolean = Type2Value[TFalse, Boolean](false)
  implicit val tTrue2Boolean = Type2Value[TTrue, Boolean](true)

  trait IfTrue[P >: TTrue <: TTrue, T] {
    type Type = T
  }
}