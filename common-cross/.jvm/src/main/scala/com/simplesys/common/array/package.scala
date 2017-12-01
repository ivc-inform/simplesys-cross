package com.simplesys.common

import scala.reflect.ClassTag
import com.simplesys.common.JVM.Strings._

package object array {
    implicit def any2Array[T: ClassTag](value: T): Array[T] = Array(value)
    //implicit def array2Any[T: ClassTag](value: Array[T]): T = value.head

    def NotValue[T: ClassTag] = Array.empty[T]

    implicit def toOption[T](value: Array[T]): Option[T] = if (value.length == 0) None else Some(value(0))

    def asArray[T: ClassTag](value: T): Array[T] = value match {
        case null => NotValue
        case _ => Array(value)
    }

    implicit def option2Array[T: ClassTag](value: Option[T]): Array[T] =
        value match {
            case None => NotValue
            case Some(value) => Array(value)
        }

    implicit class ArrayToString[T](d: Array[T]) {
        def asString: String = if (d.length == 0) strEmpty else d(0).toString
    }

    implicit class arrayOpt[T: ClassTag](array: Array[T]) {
        def unWrap: Option[T] = array.headOption
    }
}
