package com.simplesys.meta

import com.simplesys.meta.Booleans.TBool

object Comparables {
  trait Comparable {
    type CompareType <: Comparable
    type Equals[T <: CompareType] <: TBool
    type LessThan[T <: CompareType] <: TBool
    type GreatThen[T <: CompareType] <: TBool
  }

  type <[T1 <: Comparable, T2 <: T1#CompareType] = T1#LessThan[T2]
  type >[T1 <: Comparable, T2 <: T1#CompareType] = T1#GreatThen[T2]
  type ==[T1 <: Comparable, T2 <: T1#CompareType] = T1#Equals[T2]
}