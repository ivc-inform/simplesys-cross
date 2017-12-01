package com.simplesys.common

import java.io.{File, InputStream}
import java.math.BigInteger
import java.text.{DecimalFormat, DecimalFormatSymbols}
import java.time.{LocalDate, LocalDateTime, LocalTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.{ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME, ISO_LOCAL_TIME}

import scala.io.Codec.UTF8
import scala.util.Try
import com.simplesys.common.equality.SimpleEquality._
import com.simplesys.common.Time._


private[common] abstract class AbstractIterator[+A] extends Iterator[A]

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

    val lineSeparator = System.getProperty("line.separator")
    val strEmpty = ""

    implicit class CharOpts(val char: Char) {
        def toHex = String.format("%x", new BigInteger(1, char.toString.getBytes(UTF8.name)))
    }

    object Space {
        override def toString = strEmpty.space
        def apply() = strEmpty.space
    }

    object Point {
        override def toString = "."
        def apply() = "."
    }

    val space = Space()
    val point = Point()
    val spaceC: Char = Space()(0)

    implicit class NewLine(val string: String) {

        def replaceLast(regex: String, replacement: String): String = string.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement)
        def replaceLast(regex: Char, replacement: Char): String = string.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement.toString)

        def newLine: String = string + lineSeparator
        def pathSlash: String = string + File.separator
        def pathSeparator: String = string + File.pathSeparator
        def toHex: String = string match {
            case null => "null"
            case _ => string.map(c => c.toHex).mkString("\\", "\\", "\\")
        }
        def toHexWithAscci: String = string match {
            case null => "null"
            case _ => string.map(c => c.toHex + s"(${c})").mkString("\\", "\\", "\\")
        }

        def quoted = "'" + string + "'"
        def ellipsis = s"$string ..."

        def space: String = string + " "
        def plus: String = string + " + "

        def fill(length: Int, str: String): String = {
            val spc = new StringBuilder
            for (i <- 0 to length - 1)
                spc append str
            string + spc.toString()
        }

        implicit class fltrOpts(val strings: List[String]) {
            def withOutComment = strings.filter(_.substring(0, 2) != "##")
        }

        def fill(length: Int, str: String, comment: String): String = {
            def len: Int = (length - comment.length - 2) / 2

            if (!comment.isEmpty) fill(len, str).space + comment.space + fill(len, str) else fill(len, str)
        }

        def spaces(length: Int) = fill(length, space)

        private def isLineBreak(c: Char) = c == LF || c == FF
        private def apply(n: Int): Char = string charAt n

        private def linesWithSeparators: Iterator[String] = new AbstractIterator[String] {
            private val len = string.length
            private var index = 0
            def hasNext: Boolean = index < len
            def next(): String = {
                if (index >= len) throw new NoSuchElementException("next on empty iterator")
                val start = index
                while (index < len && !isLineBreak(apply(index))) index += 1
                index += 1
                string.substring(start, index min len)
            }
        }

        def stripMargin1(indent: Int, marginChar: Char = '|'): String = {
            val buf = new StringBuilder
            for (line <- linesWithSeparators) {
                val len = line.length
                var index = 0
                while (index < len && line.charAt(index) <= ' ') index += 1
                buf append
                  (if (index < len && line.charAt(index) == marginChar) strEmpty.spaces(indent) + line.substring(index + 1) else strEmpty.spaces(indent) + line)
            }
            buf.toString
        }

        def Substring(beginIndex: Int, endIndex: Int): String = {
            var _beginIndex = 0

            if (beginIndex >= 0)
                _beginIndex = beginIndex

            if (endIndex < 0)
                string.substring(_beginIndex)
            else
                string.substring(_beginIndex, endIndex)
        }

        def unCapitalize: String =
            if (string.isNull) null
            else if (string.length == 0) strEmpty
            else {
                val chars = string.toCharArray
                chars(0) = chars(0).toLower
                new String(chars)
            }

        def chmp: String = if (string != null) string.reverse.dropWhile("\n ".contains(_)).reverse else string

        def trplQuotWrp = "\"\"\"" + string.chmp + "\"\"\""

        def strplQuotWrp = "s" + "\"\"\"" + string + "\"\"\""

        def trimm: String = {
            var res = string.trim
            if (res.head.toString === lineSeparator)
                res = res.substring(1)
            if (res.last.toString === lineSeparator)
                res = res.substring(0, res.length - 1)
            res
        }

        def ltrim: String = {
            val pattern = """(\s*)(.*)""".r("leftSpacers", "another")
            var res = ""
            var step = 0
            for (x <- pattern findAllMatchIn string) {
                if (step == 0) {
                    res += x.group("another").asString
                    step += 1
                } else {
                    res += x.group("leftSpacers").asString
                    res += x.group("another").asString
                }
            }
            res
        }

        def rtrim: String = {
            val pattern = """(\S*)(\s*)""".r("another", "spacers")
            var res = ""
            val machers = (pattern findAllMatchIn string).toSeq
            var step = 1
            for (x <- machers) {
                step += 1
                res += x.group("another").asString
                if (step < machers.length)
                    res += x.group("spacers").asString
            }
            res
        }

        def delLastChar = {
            val s = string.trim

            if (s.length == 0)
                s
            else
                s.substring(0, s.length - 1).trim
        }

        def decodePercentEncoded: String =
            if (string.isNull) null
            else if (string.length == 0) strEmpty
            else {
                var inx = 0
                val res = new StringBuilder(strEmpty)
                while (inx < string.length) {
                    val curr = string.charAt(inx)
                    if (curr == '%') {
                        if (string.charAt(inx + 1) == 'u') {
                            val ch = Integer.parseInt(string.substring(inx + 2, inx + 6), 16).toChar
                            res append ch
                            inx += 6
                        }
                        else {
                            val ch = Integer.parseInt(string.substring(inx + 1, inx + 3), 16).toChar
                            res append ch
                            inx += 3

                        }
                    }
                    else {
                        res append curr
                        inx += 1
                    }
                }
                res.toString()
            }

        def asString: String = if (string.isNull) strEmpty else string
        def asOpt: Option[String] = if (string.isNull || string.isEmpty) None else Some(string)
        def isNull: Boolean = string == null
        def asInt: Int = if (string.isNull) 0 else string.toInt
        def asIntOpt: Option[Int] = if (string.isNull || string.isEmpty) None else Some(string.asInt)
        def asLong: Long = if (string.isNull) 0 else string.toLong
        def asLongOpt: Option[Long] = if (string.isNull || string.isEmpty) None else Some(string.asLong)
        def asNumber: BigDecimal = if (string.isNull) 0 else BigDecimal(string)
        def delQuote: String = Strings.delQuote(string)
        def asDouble: Double = {
            if (string.isNull) 0
            else {
                val symbols = new DecimalFormatSymbols()
                val df = new DecimalFormat()
                symbols setDecimalSeparator DecimalSeparator
                df setDecimalFormatSymbols symbols
                //string.split("+").foldLeft(0.0)((a, b) => a + (df parse b).doubleValue())
                DecimalSeparator match {
                    case ',' =>
                        (df parse string.replace('.', DecimalSeparator)).doubleValue()
                    case '.' =>
                        (df parse string.replace(',', DecimalSeparator)).doubleValue()
                }
            }
        }

        def asBoolean: Boolean =
            if (!string.isNull) string.toLowerCase match {
                case "true" => true
                case "false" => false
                case _ => throw new IllegalArgumentException(s"For input string: '${string}'")
            } else false

        def asBooleanOpt: Option[Boolean] =
            if (string.isNull || strEmpty.isEmpty) None else string.toLowerCase match {
                case "true" => Some(true)
                case "false" => Some(false)
                case _ => throw new IllegalArgumentException(s"For input string: '${string}'")
            }

        def asBooleanTry: Try[Boolean] =
            Try(string.asBoolean)

        def asDoubleTry: Try[Double] =
            Try(string.asDouble)

        def asLongTry: Try[Long] =
            Try(string.asLong)

        def asNumberTry: Try[BigDecimal] =
            Try(string.asNumber)

        def insert(strInseting: String, pos: Int): NewLine = {
            new NewLine(string.substring(0, pos) + strInseting + string.substring(pos))
        }

        def like(list: Seq[String]): Boolean = list.exists(item => string.indexOf(item) != -1)
    }

    object NewLine {
        def apply(string: String) = lineSeparator + string
        def apply() = lineSeparator
        override def toString = lineSeparator
    }

    val newLine = NewLine()

    implicit class stringToDate(val string: String) {
        def toLocalDateTime(dateTimeFormatter: DateTimeFormatter = SS_LOCAL_DATE_TIME): LocalDateTime = {
            if (string.contains("Z")) {
                val systemZone = ZoneId.systemDefault()
                val localDateTime = LocalDateTime.parse(string, SS_LOCAL_DATE_TIME_Z)
                val currentOffsetForMyZone = systemZone.getRules.getOffset(localDateTime)
                localDateTime.plusSeconds(currentOffsetForMyZone.getTotalSeconds)
            }
            else if (string.contains("T"))
                LocalDateTime.parse(string, ISO_LOCAL_DATE_TIME)
            else
                LocalDateTime.parse(string, dateTimeFormatter)

        }
        def toLocalDate(dateTimeFormatter: DateTimeFormatter = ISO_LOCAL_DATE): LocalDate = LocalDate.parse(string, dateTimeFormatter)
        def toLocalTime(dateTimeFormatter: DateTimeFormatter = ISO_LOCAL_TIME): LocalTime = LocalTime.parse(string, dateTimeFormatter)
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

    object fill {
        def apply(length: Int, str: String): String = strEmpty.fill(length, str)
        def apply(length: Int, str: String, comment: String): String = strEmpty.fill(length, str, comment)
        def apply(comment: String): String = strEmpty.fill(commentLength, "/", comment)
    }

    implicit class NewLine1(val x: newLine.type) {
        def newLine: String = x.toString + lineSeparator
    }

    val rigthRuleSlash = "/"
    val plus = "".space + "+".space

    object spaces {
        def apply(length: Int) = strEmpty spaces length
    }

    implicit class DoubleOpts(val value: Double) {
        def asString = (new DecimalFormat("########################################.####################")).format(value)
    }
}
