package com.simplesys.isc.dataBinging

import io.circe.Json._
import io.circe.{Json, JsonObject}

trait DSResponseBase {
    val data: Json
    val status: Int
    val totalRows: Option[Int]
}
case class DSResponse(data: Json, status: Int, totalRows: Option[Int] = None) extends DSResponseBase

case class DSResponseOk(data: Json = Json.Null, status: Int = RPCResponse.statusSuccess, totalRows: Option[Int] = None) extends DSResponseBase

case class ErrorData(message: String, stackTrace: String)

case class DSResponseFailureEx(data: Json, status: Int = RPCResponse.statusFailure, totalRows: Option[Int] = None) extends DSResponseBase

case class Response(response: DSResponse)
