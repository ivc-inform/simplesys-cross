package com.simplesys.control

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.reflect.ClassTag
//import scala.collection.SeqView
import scala.language.reflectiveCalls

object ControlStructs {
    def using[A <: {def close() : Unit}, B](param: A)(f: A => B): B =
        try {
            f(param)
        }
        finally {
            param close
        }

    def boolMapList[T](test: => Boolean)(block: => T): List[T] = {
        val res = new ListBuffer[T]

        while (test)
            res += block

        res toList
    }

    def boolMapArray[T: ClassTag](test: => Boolean)(block: => T): Array[T] = {
        val res = new ArrayBuffer[T]

        while (test)
            res += block

        res toArray
    }
}