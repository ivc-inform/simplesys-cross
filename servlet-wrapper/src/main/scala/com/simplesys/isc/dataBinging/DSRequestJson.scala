package com.simplesys.isc.dataBinging

import io.circe.Json

case class Transaction(transactionNum: Option[Int] = None, operations: Vector[DSRequest] = Vector.empty)

case class DSRequest(
                      startRow: Option[Int] = None,
                      endRow: Option[Int] = None,
                      textMatchStyle: Option[String] = None,
                      sortBy: Option[Vector[Json]] = None,
                      transaction: Option[Transaction] = None,
                      data: Option[Json] = None, oldValues: Option[Json] = None
                    )
