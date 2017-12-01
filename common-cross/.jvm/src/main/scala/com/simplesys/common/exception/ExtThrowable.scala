package com.simplesys.common.exception

import java.io.{PrintWriter, StringWriter}

object ExtThrowable {
  def apply(t: Throwable) = new ExtThrowable(t)
}

class ExtThrowable(t: Throwable) {
  def getStackTraceString: String = {
    val stackTrace = new StringWriter()
    t.printStackTrace(new PrintWriter(stackTrace))
    stackTrace.toString
  }

  def getMessage = t.getMessage
}
