package com.simplesys.common.JVM

import java.io.InputStream
import java.text.DecimalFormat

import com.simplesys.common.Strings._
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringEscapeUtils

object Strings {

    implicit class dblQuoteInt(int: Int) {
        def dblQuoted = "\"" + StringEscapeUtils.escapeJava(int.toString) + "\""
        def dblQuotedJS = "\"" + StringEscapeUtils.escapeEcmaScript(int.toString) + "\""
        def quoted = "'" + int.toString + "'"
    }

    implicit class dblQuoteString(val string: String) {
        def dblQuoted = "\"" + StringEscapeUtils.escapeJava(string) + "\""
        def escapeJava = StringEscapeUtils.escapeJava(string)
        def escapeJson = StringEscapeUtils.escapeJson(string)
        def escapeEcmaScript = StringEscapeUtils.escapeEcmaScript(string)
        def dblQuotedJS = "\"" + StringEscapeUtils.escapeEcmaScript(string) + "\""
        def asInt: Int = if (string.isNull) 0 else string.toInt
    }

    implicit class unQuoteString(val string: String) {
        def isNull: Boolean = string == null
        def delQuote: String = com.simplesys.common.Strings.delQuote(string)
        def unQuoted: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeJava(string).delQuote
        def unescapeJson: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeJson(string)
        def unescapeEcmaScript: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeEcmaScript(string)
        def unQuotedJS: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeEcmaScript(string).delQuote
    }

    implicit class stringToBigDecimal(string: String) {
        def toBigDecimal: BigDecimal = new java.math.BigDecimal(string.unQuoted)
    }

    implicit class RegexEx(sc: StringContext) {
        def rx = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
    }

    implicit class OptStringP(optString: Option[String]) {
        def asInt: Int = {
            optString match {
                case None => 0
                case Some(x) => x.asInt
            }
        }
    }

    implicit class InputStreamOpts(stream: InputStream) {
        def asString: String = IOUtils.toString(stream, "UTF-8")
    }

    implicit class ClassOpts(cls: Class[_]) {
        def asCanonicalName: String = cls.getCanonicalName.replace("$", strEmpty)
    }

    implicit class NewLine(val string: String) {
        def toInputStream: InputStream = IOUtils.toInputStream(string, "UTF-8")
    }
}
