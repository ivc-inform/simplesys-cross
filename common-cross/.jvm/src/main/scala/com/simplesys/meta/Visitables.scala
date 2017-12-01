package com.simplesys.meta

object Visitables {
  trait TypeVisitor {
    type ResultType
  }

  trait Visitable[T <: TypeVisitor] {
    type Accept[T2 <: T] <: T2#ResultType
  }
}