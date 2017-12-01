package com.simplesys.isc.dataBinging

import io.circe.Json

case class RPCResponseData(status: Int, login: Option[String] = None, userId: Option[Long] = None, captionUser: Option[String] = None, codeGroup: Option[String] = None, simpleSysContextPath: Option[String] = None, errorMessage: Option[String] = None)

object RPCResponse {
    val statusSuccess = 0
    val statusOffline = 1
    val statusFailure = -1
    val statusValidationError = -4
    val statusLoginIncorrect = -5
    val statusMaxLogginAttemptsExceeded = -6
    val statusLoginRequired = -7
    val statusLoginSuccess = -8
    val statusUpdateWithoutPkError = -9
    val statusTransactionFailed = -10
    val statusTransportError = -90
    val statusUnknownHostError = -91
    val statusConnetionResetError = -92
    val statusServerTimeout = -100
}

case class RPCResponse(data: Json)

