package com.simplesys.isc.dataBinging

import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

object DSResponse {
    def DSResponseOk = DSResponse(data = Json.Null, status = RPCResponse.statusSuccess)

    def DSResponseFailureEx(message: String, stackTrace: String) = DSResponse(data = Error(ErrorData(message, stackTrace)).asJson, status = RPCResponse.statusFailure)

}

case class DSResponse(
                       data: Json,
                       status: Int,
                       totalRows: Option[Int] = None,
                       startRow: Option[Int] = None,
                       endRow: Option[Int] = None,
                       urlExportFile: Option[String] = None
                     )

case class Error(error: ErrorData)

case class ErrorData(message: String, stackTrace: String)

case class Response(response: DSResponse)
