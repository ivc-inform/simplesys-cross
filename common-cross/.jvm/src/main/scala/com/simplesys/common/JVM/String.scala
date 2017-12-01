package com.simplesys.common.JVM

import java.io.InputStream
import java.text.DecimalFormat
import java.time._
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter._

import com.simplesys.common.JVM.Time._
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
        def quoted = "'" + string + "'"
        def ellipsis = s"$string ..."
    }

    implicit class fltrOpts(val strings: List[String]) {
        def withOutComment = strings.filter(_.substring(0, 2) != "##")
    }

    implicit class unQuoteString(val string: String) {
        def delQuote: String = Strings.delQuote(string)
        def unQuoted: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeJava(string).delQuote
        def unescapeJson: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeJson(string)
        def unescapeEcmaScript: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeEcmaScript(string)
        def unQuotedJS: String = if (string.isNull || string.isEmpty) strEmpty else StringEscapeUtils.unescapeEcmaScript(string).delQuote
    }

    implicit class stringToBigDecimal(string: String) {
        def toBigDecimal: BigDecimal = new java.math.BigDecimal(string.unQuoted)
    }

    implicit class stringToDate(val string: String) {
        def toLocalDateTime(dateTimeFormatter: DateTimeFormatter = SS_LOCAL_DATE_TIME): LocalDateTime = {
            if (string.contains("Z")) {
                val systemZone = ZoneId.systemDefault()
                val localDateTime = LocalDateTime.parse(string.unQuoted, SS_LOCAL_DATE_TIME_Z)
                val currentOffsetForMyZone = systemZone.getRules.getOffset(localDateTime)
                localDateTime.plusSeconds(currentOffsetForMyZone.getTotalSeconds)
            }
            else if (string.contains("T"))
                LocalDateTime.parse(string.unQuoted, ISO_LOCAL_DATE_TIME)
            else
                LocalDateTime.parse(string.unQuoted, dateTimeFormatter)

        }
        def toLocalDate(dateTimeFormatter: DateTimeFormatter = ISO_LOCAL_DATE): LocalDate = LocalDate.parse(string.unQuoted, dateTimeFormatter)
        def toLocalTime(dateTimeFormatter: DateTimeFormatter = ISO_LOCAL_TIME): LocalTime = LocalTime.parse(string.unQuoted, dateTimeFormatter)
    }

    def localDateTime2Str(localDateTime: LocalDateTime, dateTimeFormatter: DateTimeFormatter = SS_LOCAL_DATE_TIME): String = localDateTime.format(dateTimeFormatter)
    def utcDateTime2Str(utcDateTime: LocalDateTime, dateTimeFormatter: DateTimeFormatter = SS_LOCAL_DATE_TIME): String = {
        val systemZone = ZoneId.systemDefault()
        val currentOffsetForMyZone = systemZone.getRules.getOffset(utcDateTime)

        utcDateTime.plusSeconds(currentOffsetForMyZone.getTotalSeconds).format(dateTimeFormatter)
    }

    implicit class LocalDateTimeOpt(localDateTime: LocalDateTime) {
        def getMillis: Long = localDateTime.atZone(ZoneId.systemDefault()).toInstant.toEpochMilli
        def asString(dateTimeFormatter: DateTimeFormatter = SS_LOCAL_DATE_TIME): String = localDateTime2Str(localDateTime, dateTimeFormatter)

    }

    implicit class LonfToLocalDateTime(millis: Long) {

        import java.time.{Instant, ZoneId}

        def toLocalDateTime: LocalDateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault).toLocalDateTime
        def toLocalDate: LocalDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault).toLocalDate
        def toLocalTime: LocalTime = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault).toLocalTime
    }

    implicit def StringOpts2String(strOpts: NewLine): String = strOpts.asString

    implicit class DoubleOpts(val value: Double) {
        def asString = (new DecimalFormat("########################################.####################")).format(value)
    }

    val rigthRuleSlash = "/"
    val plus = "".space + "+".space

    object spaces {
        def apply(length: Int) = strEmpty spaces length
    }

    object fill {
        def apply(length: Int, str: String): String = strEmpty.fill(length, str)
        def apply(length: Int, str: String, comment: String): String = strEmpty.fill(length, str, comment)
        def apply(comment: String): String = strEmpty.fill(commentLength, "/", comment)
    }

    implicit class NewLine1(val x: newLine.type) {
        def newLine: String = x.toString + lineSeparator
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
