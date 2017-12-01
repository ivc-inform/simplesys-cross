package com.simplesys.meta.example

import com.simplesys.meta.HtgLists._
import com.simplesys.meta.Nats._
import com.simplesys.meta.Utils._

object HtgListsExamples {
  // Create Heterogeneous list

  val list1 = 10 :: true ::(10.22, "Hello World") :: HtgNil

  // Extract the second element, note that the element type
  // information is preserved and we can safely perform a
  // boolean and operation

  list1.nth[_1] && false

  // Create another HList, note the use of an operator in
  // the type expression
  val list2: Double :: String :: Boolean :: HtgNil = 1.1 :: "string" :: false :: HtgNil

  // Replace the second element in the list, it used to be a String, but now it's an Int
  val list3 = list2.remove[_1].insert(_1, 14)

  // Type information preserved, we can use an Int operation on the element
  list3.nth[_1] * 10

  // Append l2 to l1
  val list4 = list1 ::: list2

  // Statically check that the length of l4 is 6
  type T = Equal[_6, list4.Length]
}