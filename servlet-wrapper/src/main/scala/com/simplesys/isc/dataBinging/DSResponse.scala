package com.simplesys.isc.dataBinging

import io.circe.Json._
import io.circe.{Json, JsonObject}

trait DSResponseBase {
    val data: Json
    val status: Int
    val totalRows: Option[Int]
}
case class DSResponse(data: Json, status: Int, totalRows: Option[Int] = None) extends DSResponseBase

case class DSResponseFailureEx(message: String, stackTrace: String) extends DSResponseBase {


    override val totalRows = None

    override val status = RPCResponse.statusFailure

    override val data = fromJsonObject(JsonObject.singleton("error", fromJsonObject(JsonObject.fromIterable(Seq("message" → fromString(message), "stackTrace" → fromString(stackTrace))))))
}
