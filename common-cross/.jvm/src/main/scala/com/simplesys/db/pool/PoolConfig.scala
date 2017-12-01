package com.simplesys.db.pool

case class PoolConfig(className: Option[String], url: String, user: String, password: String, initialSize: Int, maxSize: Option[Int], fetchSize: Int, timeZone: String, waitTimeout: Option[Int] = None, maxConnectionReuseTime: Option[Long])
