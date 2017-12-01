package com.simplesys.common

import java.text.DecimalFormatSymbols


object Strings {
    val commentLength = 200
    val indentSize = 4

    private final val LF: Char = 0x0A
    private final val FF: Char = 0x0C

    def DecimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator
    def DecimalSeparator_(value: Char) {
        DecimalFormatSymbols.getInstance() setDecimalSeparator value
    }

    def GroupingSeparator = DecimalFormatSymbols.getInstance().getGroupingSeparator.toString
    def GroupingSeparator_(value: Char) {
        DecimalFormatSymbols.getInstance() setGroupingSeparator value
    }

    def delQuote(str: String): String = {
        val s = str.trim
        if (s.head == '"' && s.last == '"')
            s.drop(1).dropRight(1)
        else
            s
    }



}
