package com.simplesys.isc.dataBinging

import io.circe.JsonObject
import io.circe.Json._

case class DataSource(json: JsonObject) {
    override def toString = s"isc.DataSource.create(${fromJsonObject(json).noSpaces})"
    def toPrettyString = s"isc.DataSource.create(${fromJsonObject(json).spaces4})"
}
