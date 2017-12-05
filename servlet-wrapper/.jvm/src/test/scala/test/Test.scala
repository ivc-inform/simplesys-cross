package test

import com.simplesys.isc.dataBinging.{DSResponseFailureEx, DSResponseOk, ErrorData}
import com.simplesys.circe.Circe._
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalatest.FunSuite

class Test extends FunSuite{
  test("1") {
      println(DSResponseFailureEx(ErrorData(message = "message", stackTrace = "stackTrace").asJson).asJson.spaces41)
      println(DSResponseOk().asJson.spaces41)
  }
}
