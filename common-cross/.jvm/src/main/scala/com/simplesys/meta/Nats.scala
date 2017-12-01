package com.simplesys.meta

import com.simplesys.meta.Visitables.TypeVisitor
import com.simplesys.meta.Addables.Addable
import com.simplesys.meta.Comparables.Comparable
import com.simplesys.meta.Booleans.{TFalse, TTrue, TBool}
import com.simplesys.meta.Utils.{Type2Value, Invalid}

object Nats {
  sealed trait Nat extends Addable with Comparable {
    type AddType = Nat
    type Add[T <: Nat] <: Nat

    type CompareType = Nat
    type Equals[T <: Nat] <: TBool
    type LessThan[T <: Nat] <: TBool
    type GreatThen[T <: Nat] <: TBool

    type Pre
    type Is0 <: TBool
    type Accept[T <: NatVisitor] <: T#ResultType
    def toInt: Int
  }

  trait NatVisitor extends TypeVisitor {
    type Visit0 <: ResultType
    type VisitSucces[T <: Nat] <: ResultType
  }

  final class _0 extends Nat {
    type Add[T <: Nat] = T
    type Equals[T <: Nat] = T#Is0
    type LessThan[T <: Nat] = T#Is0#Not
    type Pre = Invalid
    type Is0 = TTrue
    type Accept[T <: NatVisitor] = T#Visit0
    def toInt = 0
  }

  final case class Succes[P <: Nat](toInt: Int) extends Nat {

    trait EqualsVisitor extends NatVisitor {
      type Visit0 = TFalse
      type VisitSucc[T <: Nat] = P#Equals[T]

      type ResultType = TBool
    }

    trait LessThanVisitor extends NatVisitor {
      type Visit0 = TFalse
      type VisitSucc[T <: Nat] = P#LessThan[T]

      type ResultType = TBool
    }

    trait GreatThanVisitor extends NatVisitor {
      type Visit0 = TFalse
      type VisitSucc[T <: Nat] = P#GreatThen[T]

      type ResultType = TBool
    }

    type Add[T <: Nat] = Succes[P#Add[T]]
    type Equals[T <: Nat] = T#Accept[EqualsVisitor]
    type LessThan[T <: Nat] = T#Accept[LessThanVisitor]
    type Pre = P
    type Is0 = TFalse
    type Accept[T <: NatVisitor] = T#VisitSucces[P]

    def +[T <: Nat](n: T): Add[T] = Succes[P#Add[T]](toInt + n.toInt)
  }

  type _1 = Succes[_0]
  type _2 = Succes[_1]
  type _3 = Succes[_2]
  type _4 = Succes[_3]
  type _5 = Succes[_4]
  type _6 = Succes[_5]
  type _7 = Succes[_6]
  type _8 = Succes[_7]
  type _9 = Succes[_8]
  type _10 = Succes[_9]

  val _0 = new _0
  val _1 = new _1(1)
  val _2 = new _2(2)
  val _3 = new _3(3)
  val _4 = new _4(4)
  val _5 = new _5(5)
  val _6 = new _6(6)
  val _7 = new _7(7)
  val _8 = new _8(8)
  val _9 = new _9(9)
  val _10 = new _10(10)

  implicit val _0toInt = Type2Value[_0, Int](0)
  implicit def succes2Int[T <: Nat](implicit v: Type2Value[T, Int]) = Type2Value[Succes[T], Int](1 + v.value)

  def toInt[T <: Nat](v: T)(implicit function: Type2Value[T, Int]) = function()
}