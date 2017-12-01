package com.simplesys.meta

object HtgLists {

  import Nats._
  import Utils._

  sealed trait HtgList {
    type Head
    type Tail <: HtgList
    type Append[T <: HtgList] <: HtgList
    type ReverseAppend[T <: HtgList] <: HtgList
    type Length <: Nat
  }

  final class HtgNil extends HtgList {
    type Head = Nothing
    type Tail = HtgNil
    type Append[T <: HtgList] = T
    type ReverseAppend[T <: HtgList] = T
    type Length = _0

    def ::[T](v: T) = HtgCons(v, this)
    def :::[T <: HtgList](list: T) = list
    def reverse = this
  }

  val HtgNil = new HtgNil()

  final case class HtgCons[H, T <: HtgList](head: H, tail: T) extends HtgList {
    type This = HtgCons[H, T]
    type Head = H
    type Tail = T
    type Append[L <: HtgList] = HtgCons[H, T#Append[L]]
    type ReverseAppend[L <: HtgList] = Tail#ReverseAppend[HtgCons[H, L]]
    type Length = Succes[T#Length]
    type GetByType[N <: Nat, E] = GetByTypeFn[This, N, E]
    type ReplaceByType[N <: Nat, E] = ReplaceByTypeFn[This, N, E]

    def ::[T](v: T) = HtgCons(v, this)
    def :::[T <: HtgList](list: T)(implicit function: AppendFn[T, This]) = function(list, this)
    def nth[N <: Nat](implicit function: NthFn[This, N]): NthType[This, N] = function(this)
    def reverse(implicit function: ReverseAppendFn[This, HtgNil]) = function(this, HtgNil)
    def remove[N <: Nat](implicit function: RemoveNthFn[This, N]) = function(this)

    def insert[N <: Nat, E](n: N, elem: E)(implicit function: InsertNthFn[This, N, E]): InsertNthType[This, N, E] = insert[N, E](elem)
    def insert[N <: Nat, E](elem: E)(implicit function: InsertNthFn[This, N, E]): InsertNthType[This, N, E] = function(this, elem)

    def replaceByType[N <: Nat, E](n: N, elem: E)(implicit function: ReplaceByType[N, E]) = function(this, elem)
    def getByType[N <: Nat, E](implicit function: GetByType[N, E]): E = function(this)
  }

  type ::[H, T <: HtgList] = HtgCons[H, T]

  case class AppendFn[T1 <: HtgList, T2 <: HtgList](function: (T1, T2) => T1#Append[T2]) extends Function2Wrapper(function)
  case class ReverseAppendFn[T1 <: HtgList, T2 <: HtgList](function: (T1, T2) => T1#ReverseAppend[T2]) extends Function2Wrapper(function)
  case class NthFn[T <: HtgList, N <: Nat](function: T => NthType[T, N]) extends Function1Wrapper(function)

  final class NthVisitor[T <: HtgList] extends NatVisitor {
    type Visit0 = T#Head
    type VisitSucces[Pre <: Nat] = Pre#Accept[NthVisitor[T#Tail]]

    type ResultType = Any
  }

  type NthType[T <: HtgList, N <: Nat] = N#Accept[NthVisitor[T]]

  final class RemoveNthVisitor[T <: HtgList] extends NatVisitor {
    type Visit0 = T#Tail
    type VisitSucces[Pre <: Nat] = HtgCons[T#Head, Pre#Accept[RemoveNthVisitor[T#Tail]]]
    type ResultType = HtgList
  }

  type RemoveNthType[T <: HtgList, N <: Nat] = N#Accept[RemoveNthVisitor[T]]
  case class RemoveNthFn[T <: HtgList, N <: Nat](function: T => RemoveNthType[T, N]) extends Function1Wrapper(function)

  final class InsertNthVisitor[L <: HtgList, T] extends NatVisitor {
    type Visit0 = HtgCons[T, L]
    type VisitSucces[Pre <: Nat] = HtgCons[L#Head, Pre#Accept[InsertNthVisitor[L#Tail, T]]]
    type ResultType = HtgList
  }

  type InsertNthType[T <: HtgList, N <: Nat, E] = N#Accept[InsertNthVisitor[T, E]]
  case class InsertNthFn[T <: HtgList, N <: Nat, E](function: (T, E) => InsertNthType[T, N, E]) extends Function2Wrapper(function)

  case class ReplaceByTypeFn[T <: HtgList, N <: Nat, E](function: (T, E) => T) extends Function2Wrapper(function)
  case class GetByTypeFn[T <: HtgList, N <: Nat, E](function: T => E) extends Function1Wrapper(function)

  // Implicits

  // Append
  implicit def hlistNilAppender[T <: HtgList] = AppendFn[HtgNil, T]((v: HtgNil, list: T) => list)
  implicit def hlistConsAppender[H, T1 <: HtgList, T2 <: HtgList, R <: HtgList]
  (implicit function: AppendFn[T1, T2]) = AppendFn[HtgCons[H, T1], T2]((list1: HtgCons[H, T1], list2: T2) => HtgCons(list1.head, function(list1.tail, list2)))

  // Reverse append
  implicit def hlistNilReverseAppender[T <: HtgList] = ReverseAppendFn[HtgNil, T]((v: HtgNil, list: T) => list)
  implicit def hlistConsReverseAppender[H, T1 <: HtgList, T2 <: HtgList, R <: HtgList]
  (implicit function: ReverseAppendFn[T1, HtgCons[H, T2]]) = ReverseAppendFn[HtgCons[H, T1], T2]((list1: HtgCons[H, T1], list2: T2) => function(list1.tail, HtgCons(list1.head, list2)))

  //Nts
  implicit def hlistConsNth0[H, T <: HtgList] = NthFn[HtgCons[H, T], _0](list => list.head)
  implicit def hlistConsNth[H, T <: HtgList, Pos <: Nat]
  (implicit function: NthFn[T, Pos]) = NthFn[HtgCons[H, T], Succes[Pos]](list => function(list.tail))

  //Remove nts
  implicit def hlistRemoveNth0[H, T <: HtgList] = RemoveNthFn[HtgCons[H, T], _0](list => list.tail)
  implicit def hlistRemoveNth[H, T <: HtgList, Pos <: Nat]
  (implicit function: RemoveNthFn[T, Pos]) = RemoveNthFn[HtgCons[H, T], Succes[Pos]](list => HtgCons(list.head, function(list.tail)))

  //Insert nth
  implicit def hlistInsertNth0[L <: HtgList, E] = InsertNthFn[L, _0, E]((list, elem) => HtgCons(elem, list))
  implicit def hlistInsertNth[H, T <: HtgList, Pos <: Nat, E](implicit function: InsertNthFn[T, Pos, E]) =
    InsertNthFn[HtgCons[H, T], Succes[Pos], E]((list, elem) => HtgCons(list.head, function(list.tail, elem)))

  //Replace  by type
  implicit def hlistReplaceByType0[T <: HtgList, E] = ReplaceByTypeFn[HtgCons[E, T], _0, E]((list, elem) => HtgCons(elem, list.tail))
  implicit def hlistReplaceByTypeNthMatch[T <: HtgList, Pos <: Nat, E](implicit function: ReplaceByTypeFn[T, Pos, E]) =
    ReplaceByTypeFn[HtgCons[E, T], Succes[Pos], E]((list, elem) => HtgCons(elem, function(list.tail, elem)))
  implicit def hlistReplaceByTypeNthNotMatch[H, T <: HtgList, Pos <: Nat, E](implicit function: ReplaceByTypeFn[T, Pos, E]) =
    ReplaceByTypeFn[HtgCons[H, T], Pos, E]((list, elem) => HtgCons(list.head, function(list.tail, elem)))

  //Get by type
  implicit def hlistGetByType0[T <: HtgList, E] = GetByTypeFn[HtgCons[E, T], _0, E](list => list.head)
  implicit def hlistGetByTypeNthMatch[T <: HtgList, Pos <: Nat, E](implicit function: GetByTypeFn[T, Pos, E]) =
    GetByTypeFn[HtgCons[E, T], Succes[Pos], E](list => function(list.tail))
  implicit def hlistGetByTypeNthNotMatch[H, T <: HtgList, Pos <: Nat, E](implicit function: GetByTypeFn[T, Pos, E]) =
    GetByTypeFn[HtgCons[H, T], Pos, E](list => function(list.tail))
}

