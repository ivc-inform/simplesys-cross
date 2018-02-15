package com.simplesys.circe

import java.time.LocalDateTime

import io.circe.Json._
import io.circe.{HCursor, Json, JsonObject, Printer}

object Circe {

    implicit class CirceOpt(json: Json) {

        def noSpaces1 = Printer(
            preserveOrder = true,
            dropNullKeys = true,
            indent = strEmpty
        ).pretty(json)


        private def indented(indent: String): Printer = Printer(
            preserveOrder = true,
            dropNullKeys = true,
            indent = indent,
            lbraceRight = newLine,
            rbraceLeft = newLine,
            lbracketRight = newLine,
            rbracketLeft = newLine,
            lrbracketsEmpty = newLine,
            arrayCommaRight = newLine,
            objectCommaRight = newLine,
            colonLeft = space,
            colonRight = space
        )

        def spaces21 = indented("  ").pretty(json)

        def spaces41 = indented("    ").pretty(json)

        def toPrettyString = json.spaces41

        val cursor: HCursor = json.hcursor

        def getJsonElement(key: String): Option[Json] = cursor.downField(key).as[Json] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getString(key: String): String = cursor.downField(key).as[String] match {
            case Right(x) ⇒ x
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getStringOpt(key: String): Option[String] = cursor.downField(key).as[String] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getLong(key: String): Long = cursor.downField(key).as[Long] match {
            case Right(x) ⇒ x
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getLongOpt(key: String): Option[Long] = cursor.downField(key).as[Long] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getInt(key: String): Int = cursor.downField(key).as[Int] match {
            case Right(x) ⇒ x
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getIntOpt(key: String): Option[Int] = cursor.downField(key).as[Int] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getDouble(key: String): Double = cursor.downField(key).as[Double] match {
            case Right(x) ⇒ x
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getDoubleOpt(key: String): Option[Double] = cursor.downField(key).as[Double] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getLocalDateTime(key: String): LocalDateTime = cursor.downField(key).as[String] match {
            case Right(x) ⇒ x.toLocalDateTime()
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getLocalDateTimeOpt(key: String): Option[LocalDateTime] = cursor.downField(key).as[String] match {
            case Right(x) ⇒ Some(x.toLocalDateTime())
            case Left(_) ⇒ None
        }

        def getJsonObject(key: String): Json = cursor.downField(key).as[Json] match {
            case Right(x) ⇒ x
            case Left(_) ⇒ throw new RuntimeException(s"Key $key not found.")
        }

        def getJsonObjectOpt(key: String): Option[Json] = cursor.downField(key).as[Json] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getBoolean(key: String): Boolean = cursor.downField(key).as[Boolean] match {
            case Right(x) ⇒ x
            case Left(failure) ⇒ throw failure
        }

        def getBooleanOpt(key: String): Option[Boolean] = cursor.downField(key).as[Boolean] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getBigDecimal(key: String): BigDecimal = cursor.downField(key).as[BigDecimal] match {
            case Right(x) ⇒ x
            case Left(failure) ⇒ throw failure
        }

        def getBigDecimalOpt(key: String): Option[BigDecimal] = cursor.downField(key).as[BigDecimal] match {
            case Right(x) ⇒ Some(x)
            case Left(_) ⇒ None
        }

        def getJsonList(key: String): Vector[Json] = cursor.downField(key).as[Json] match {
            case Right(x) ⇒ x.asArray match {
                case None ⇒ Vector.empty
                case Some(x) ⇒ x
            }
            case Left(_) ⇒ Vector.empty
        }

        def getJsonListOpt(key: String): Option[Vector[Json]] = cursor.downField(key).as[Json] match {
            case Right(x) ⇒ x.asArray match {
                case None ⇒ None
                case Some(x) ⇒ Some(x)
            }
            case Left(_) ⇒ None
        }

        def ++(_json: Json): Json = json.asObject match {
            case None ⇒ _json
            case Some(jsonObject) ⇒
                _json.asObject match {
                    case None ⇒ json
                    case Some(_jsonObject) ⇒
                        fromFields(jsonObject.toMap ++ _jsonObject.toMap)
                }

        }

        def ++(_json: Option[Json]): Json = json.asObject match {
            case None ⇒ _json.getOrElse(Json.Null)
            case Some(jsonObject) ⇒
                _json.getOrElse(Json.Null).asObject match {
                    case None ⇒ json
                    case Some(_jsonObject) ⇒
                        fromFields(jsonObject.toMap ++ _jsonObject.toMap)
                }

        }
    }

    implicit class Circe1Opt(json: Option[Json]) {

        def noSpaces1 = json.getOrElse(Json.Null).noSpaces1

        def spaces21 = json.getOrElse(Json.Null).spaces21

        def spaces41 = json.getOrElse(Json.Null).spaces41

        def toPrettyString = json.spaces41

        def getJsonElement(key: String): Option[Json] = json.getOrElse(Json.Null).getJsonElement(key)

        def getString(key: String): String = json.getOrElse(Json.Null).getString(key)

        def getStringOpt(key: String): Option[String] = json.getOrElse(Json.Null).getStringOpt(key)

        def getLong(key: String): Long = json.getOrElse(Json.Null).getLong(key)

        def getLongOpt(key: String): Option[Long] = json.getOrElse(Json.Null).getLongOpt(key)

        def getInt(key: String): Int = json.getOrElse(Json.Null).getInt(key)

        def getIntOpt(key: String): Option[Int] = json.getOrElse(Json.Null).getIntOpt(key)

        def getDouble(key: String): Double = json.getOrElse(Json.Null).getDouble(key)

        def getDoubleOpt(key: String): Option[Double] = json.getOrElse(Json.Null).getDoubleOpt(key)

        def getLocalDateTime(key: String): LocalDateTime = json.getOrElse(Json.Null).getLocalDateTime(key)

        def getLocalDateTimeOpt(key: String): Option[LocalDateTime] = json.getOrElse(Json.Null).getLocalDateTimeOpt(key)

        def getJsonObject(key: String): Json = json.getOrElse(Json.Null).getJsonObject(key)

        def getJsonObjectOpt(key: String): Option[Json] = json.getOrElse(Json.Null).getJsonObjectOpt(key)

        def getBoolean(key: String): Boolean = json.getOrElse(Json.Null).getBoolean(key)

        def getBooleanOpt(key: String): Option[Boolean] = json.getOrElse(Json.Null).getBooleanOpt(key)

        def getBigDecimal(key: String): BigDecimal = json.getOrElse(Json.Null).getBigDecimal(key)

        def getBigDecimalOpt(key: String): Option[BigDecimal] = json.getOrElse(Json.Null).getBigDecimalOpt(key)

        def getJsonList(key: String): Vector[Json] = json.getOrElse(Json.Null).getJsonList(key)

        def getJsonListOpt(key: String): Option[Vector[Json]] = json.getOrElse(Json.Null).getJsonListOpt(key)

        def ++(_json: Json): Json = json.getOrElse(Json.Null) ++ _json

        def ++(_json: Option[Json]): Json = json.getOrElse(Json.Null) ++ _json
    }

    implicit class Circe2Opt(jsonObject: JsonObject) {

        def noSpaces1 = fromJsonObject(jsonObject).noSpaces1

        def spaces21 = fromJsonObject(jsonObject).spaces21

        def spaces41 = fromJsonObject(jsonObject).spaces41

        def toPrettyString = jsonObject.spaces41

        def getJsonElement(key: String): Option[Json] = fromJsonObject(jsonObject).getJsonElement(key)

        def getString(key: String): String = fromJsonObject(jsonObject).getString(key)

        def getStringOpt(key: String): Option[String] = fromJsonObject(jsonObject).getStringOpt(key)

        def getLong(key: String): Long = fromJsonObject(jsonObject).getLong(key)

        def getLongOpt(key: String): Option[Long] = fromJsonObject(jsonObject).getLongOpt(key)

        def getInt(key: String): Int = fromJsonObject(jsonObject).getInt(key)

        def getIntOpt(key: String): Option[Int] = fromJsonObject(jsonObject).getIntOpt(key)

        def getDouble(key: String): Double = fromJsonObject(jsonObject).getDouble(key)

        def getDoubleOpt(key: String): Option[Double] = fromJsonObject(jsonObject).getDoubleOpt(key)

        def getLocalDateTime(key: String): LocalDateTime = fromJsonObject(jsonObject).getLocalDateTime(key)

        def getLocalDateTimeOpt(key: String): Option[LocalDateTime] = fromJsonObject(jsonObject).getLocalDateTimeOpt(key)

        def getJsonObject(key: String): Json = fromJsonObject(jsonObject).getJsonObject(key)

        def getJsonObjectOpt(key: String): Option[Json] = fromJsonObject(jsonObject).getJsonObjectOpt(key)

        def getBoolean(key: String): Boolean = fromJsonObject(jsonObject).getBoolean(key)

        def getBooleanOpt(key: String): Option[Boolean] = fromJsonObject(jsonObject).getBooleanOpt(key)

        def getBigDecimal(key: String): BigDecimal = fromJsonObject(jsonObject).getBigDecimal(key)

        def getBigDecimalOpt(key: String): Option[BigDecimal] = fromJsonObject(jsonObject).getBigDecimalOpt(key)

        def getJsonList(key: String): Vector[Json] = fromJsonObject(jsonObject).getJsonList(key)

        def getJsonListOpt(key: String): Option[Vector[Json]] = fromJsonObject(jsonObject).getJsonListOpt(key)

        def getProxyObject: Map[String, Json] = jsonObject.toMap

        def ++(_json: JsonObject): Json = fromJsonObject(jsonObject) ++ fromJsonObject(_json)

        def ++(_json: Option[JsonObject]): Json = fromJsonObject(jsonObject) ++ fromJsonObject(_json.getOrElse(JsonObject.empty))
    }

    implicit class Circe3Opt(json: Vector[Json]) {
        def toPrettyString = arr(json: _*).toPrettyString
    }

    implicit def impString(str: String): Json = fromString(str)
    implicit def impStringopt(str: Option[String]): Json = if (str.isEmpty) Json.Null else fromString(str.get)
    implicit def impStringarr(str: Array[String]): Json = if (str.isEmpty) Json.Null else fromString(str.head)

    implicit def impLong(long: Long): Json = fromLong(long)
    implicit def impLongopt(long: Option[Long]): Json = if (long.isEmpty) Json.Null else fromLong(long.get)
    implicit def impLongarr(long: Array[Long]): Json = if (long.isEmpty) Json.Null else fromLong(long.head)

    implicit def impBoolean(boolean: Boolean): Json = fromBoolean(boolean)
    implicit def impBooleanopt(boolean: Option[Boolean]): Json = if (boolean.isEmpty) Json.Null else fromBoolean(boolean.get)
    implicit def impBooleanarr(boolean: Array[Boolean]): Json = if (boolean.isEmpty) Json.Null else fromBoolean(boolean.head)

    implicit def impInt(int: Int): Json = fromInt(int)
    implicit def impIntopt(int: Option[Int]): Json = if (int.isEmpty) Json.Null else fromInt(int.get)
    implicit def impIntarr(int: Array[Int]): Json = if (int.isEmpty) Json.Null else fromInt(int.head)

    implicit def impBigDecimal(bigDecimal: BigDecimal): Json = fromBigDecimal(bigDecimal)
    implicit def impBigDecimalopt(bigDecimal: Option[BigDecimal]): Json = if (bigDecimal.isEmpty) Json.Null else fromBigDecimal(bigDecimal.get)
    implicit def impBigDecimalarr(bigDecimal: Array[BigDecimal]): Json = if (bigDecimal.isEmpty) Json.Null else fromBigDecimal(bigDecimal.head)

    implicit def impDouble(double: Double): Json = fromDouble(double) getOrElse (Json.Null)
    implicit def impDoubleopt(double: Option[Double]): Json = if (double.isEmpty) Json.Null else fromDouble(double.get).getOrElse(Json.Null)
    implicit def impDoublearr(double: Array[Double]): Json = if (double.isEmpty) Json.Null else fromDouble(double.head).getOrElse(Json.Null)

    implicit def impLocalDateTime(localDateTime: LocalDateTime): Json = fromString(localDateTime2Str(localDateTime))
    implicit def impLocalDateTime(localDateTime: Option[LocalDateTime]): Json = if (localDateTime.isEmpty) Json.Null else fromString(localDateTime2Str(localDateTime.get))
    implicit def impLocalDateTime(localDateTime: Array[LocalDateTime]): Json = if (localDateTime.isEmpty) Json.Null else fromString(localDateTime2Str(localDateTime.head))

    implicit def seq2Json(seq: Seq[(String, Json)]): Json = fromJsonObject(JsonObject.fromIterable(seq))
    
}
