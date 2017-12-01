package com.simplesys.common

class DateTime(val date: java.util.Date) {
  def this(date: Long) = this(new java.util.Date(date))
}

object DateTime {
  def apply(date: java.util.Date) = new DateTime(date)

  def apply(date: Long) = new DateTime(date)

  implicit def SD2JD(date: DateTime): java.util.Date = date date

  implicit def JD2SQLDate(date: java.util.Date) = new java.sql.Date(date getTime)

  implicit def SQLDate2JD(date: java.sql.Date) = new java.util.Date(date getTime)
}
