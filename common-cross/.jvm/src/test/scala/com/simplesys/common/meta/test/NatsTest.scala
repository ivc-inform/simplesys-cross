package com.simplesys.common.meta.test

object NatsTest {

  import com.simplesys.meta.Utils._
  import com.simplesys.meta.Nats._
  import com.simplesys.meta.Addables._

  type T1 = Equal[_0, _0 + _0]
  type T2 = Equal[_1, _1 + _0]
  type T3 = Equal[_1, _0 + _1]
  type T4 = Equal[_2, _1 + _1]
  type T5 = Equal[_7, _3 + _4]
}
