package com.simplesys.meta

object Addables {
  trait Addable {
    type AddType <: Addable
    type Add[T <: AddType] <: AddType
  }

  type +[T1 <: Addable, T2 <: T1#AddType] = T1#Add[T2]
}