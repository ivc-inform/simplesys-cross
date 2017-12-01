package com.simplesys.common

import java.io.PrintStream

import com.simplesys.common.Properties._

trait SysOptsPredef {
  Console setOut (new PrintStream(System.out, true, SysProperty("CodePage", "Cp866")))
}
