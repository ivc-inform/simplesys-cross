package com.simplesys.common.JVM

import java.time.format.DateTimeFormatter._
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField._
import java.util.Locale

import com.simplesys.common.JVM.Strings._


object Time {
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
}
