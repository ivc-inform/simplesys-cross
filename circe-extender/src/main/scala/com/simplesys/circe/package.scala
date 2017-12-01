package com.simplesys

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.{ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME, ISO_LOCAL_TIME}
import java.time.{LocalDate, LocalDateTime, LocalTime, ZoneId}

import com.simplesys.common.Time._

package object circe {
    val strEmpty = ""
    val space = " "
    val newLine = System.getProperty("line.separator")
    val point = "."

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
