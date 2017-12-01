package com.simplesys.common.meta.test

import com.simplesys.meta.HtgLists._
import org.scalatest.FunSuite

case class Y[T](res: T) {
  type TSimpleType = T
}


trait TestType[A <: HtgList, T <: HtgList]

object TestType {
  implicit def toTestType[AA <: HtgList]: TestType[AA, AA#Tail] = new TestType[AA, AA#Tail] {}
  implicit val nilFieldValueModAndReducedTypes: TestType[HtgNil, HtgNil] = new TestType[HtgNil, HtgNil] {}

  implicit def hlistFieldValueModAndReducedTypesNull[R, FH <: Y[R], FT <: HtgList, VT <: HtgList](implicit next: TestType[FT, VT]): TestType[FH :: FT, Y[FH#TSimpleType] :: VT] = new TestType[FH :: FT, Y[FH#TSimpleType] :: VT] {}
}

trait A {
  type HL <: HtgList

  val a: HtgList

  def tailTest[B <: HtgList](implicit p: TestType[a.type, B], mv: Manifest[B]) = println(mv)

  def tailTest[B <: HtgList](x: HL)(implicit p: TestType[HL, B], mv: Manifest[B]) = println(mv)

  def prn[T](implicit mv: Manifest[T]) = println(mv)
}

object B extends A {
  type HL = Y[Int] :: Y[Double] :: Y[String] :: HtgNil
  val a = Y(1) :: Y(2.5) :: Y("qqq") :: HtgNil
}

class Test extends FunSuite {
  test("1") {
    B.tailTest

    B.tailTest(B.a)
  }

  test("2") {
    class a
    class b extends a
    class c extends b
    class d extends b

    val a = new a :: new b :: new c :: new d :: new a :: new b :: new c :: HtgNil

    def prnt(l: HtgList) {
      l match {
        case HtgCons(head, tail) =>
          head match {
            case a:
              d => println("This is Class d")
            case a:
              c => println("This is Class c")
            case a:
              b => println("This is Class b")
            case a:
              a => println("This is Class a")
          }
          if (tail != HtgNil)
            prnt(tail)

      }
    }

    prnt(a)
  }
}

