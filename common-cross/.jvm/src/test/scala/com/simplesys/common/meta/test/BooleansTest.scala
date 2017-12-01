package com.simplesys.common.meta.test

import com.simplesys.meta.Utils._
import com.simplesys.meta.Booleans._

object BooleansTest {

  object And {
    type T1 = Equal[TFalse, TFalse && TFalse]
    type T2 = Equal[TFalse, TTrue && TFalse]
    type T3 = Equal[TFalse, TFalse && TTrue]
    type T4 = Equal[TTrue, TTrue && TTrue]
  }

  object Or {
    type T1 = Equal[TFalse, TFalse || TFalse]
    type T2 = Equal[TTrue, TTrue || TFalse]
    type T3 = Equal[TTrue, TFalse || TTrue]
    type T4 = Equal[TTrue, TTrue || TTrue]
  }

  object Not {
    type T1 = Equal[TTrue, TFalse#Not]
    type T2 = Equal[TFalse, TTrue#Not]
  }

  object If {
    type T1 = Equal[Int, TTrue#If[Int, Boolean]]
    type T2 = Equal[Boolean, TFalse#If[Int, Boolean]]
  }
}