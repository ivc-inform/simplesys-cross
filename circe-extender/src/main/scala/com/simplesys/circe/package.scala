package com.simplesys

import java.time.{LocalDate, LocalDateTime, LocalTime, ZoneId}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.format.DateTimeFormatter.{ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME, ISO_LOCAL_TIME}
import java.time.temporal.ChronoField._
import java.util.Locale

package object circe {
    val strEmpty = ""
    val space = " "
    val newLine = System.getProperty("line.separator")
    val point = "."

    val SS_LOCAL_DATE =
        new DateTimeFormatterBuilder()
          .appendValue(DAY_OF_MONTH, 2)
          .appendLiteral(point)
          .appendValue(MONTH_OF_YEAR, 2)
          .appendLiteral(point)
          .appendValue(YEAR)
          .toFormatter(Locale.getDefault(Locale.Category.FORMAT))

    val SS_LOCAL_TIME =
        new DateTimeFormatterBuilder()
          .appendValue(HOUR_OF_DAY, 2)
          .appendLiteral(':')
          .appendValue(MINUTE_OF_HOUR, 2)
          .optionalStart.appendLiteral(':')
          .appendValue(SECOND_OF_MINUTE, 2)
          .toFormatter(Locale.getDefault(Locale.Category.FORMAT))

    val SS_LOCAL_DATE_TIME =
        new DateTimeFormatterBuilder()
          .parseCaseInsensitive.append(SS_LOCAL_DATE)
          .appendLiteral(space)
          .append(SS_LOCAL_TIME)
          .toFormatter(Locale.getDefault(Locale.Category.FORMAT))

    val SS_LOCAL_TIME_Z = new DateTimeFormatterBuilder()
      .appendValue(HOUR_OF_DAY, 2)
      .appendLiteral(':')
      .appendValue(MINUTE_OF_HOUR, 2)
      .optionalStart()
      .appendLiteral(':')
      .appendValue(SECOND_OF_MINUTE, 2)
      .optionalStart()
      .appendFraction(NANO_OF_SECOND, 0, 9, true)
      .toFormatter(Locale.getDefault(Locale.Category.FORMAT))

    val SS_LOCAL_DATE_TIME_Z = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(ISO_LOCAL_DATE)
      .appendLiteral('T')
      .append(SS_LOCAL_TIME_Z)
      .appendOffsetId
      .toFormatter(Locale.getDefault(Locale.Category.FORMAT))

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
}
