package com.simplesys.sql

sealed abstract class SQLDialect

case object UnknownDialect extends SQLDialect

case object PosgreSQLDialect extends SQLDialect

case object OracleDialect extends SQLDialect

case object MSSQLDialect extends SQLDialect

case object H2Dialect extends SQLDialect