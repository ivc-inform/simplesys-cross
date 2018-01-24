import sbt.Keys._
import sbt.{Def, _}

object PluginDeps {
    object versions {

    }
}

object CommonDeps {
    val logbackClassic = "ch.qos.logback" % "logback-classic" % versions.logbackVersion

    val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.18.2-SNAPSHOT"
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
    val scalaTest = "org.scalatest" %% "scalatest" % versions.scalaTestVersion % Test
    val apacheCommonsIO = "commons-io" % "commons-io" % versions.apacheCommonsIOVersion

    object versions {
        val logbackVersion = "1.2.3"
        val scalaTestVersion = "3.0.4"
        val circeVersion = "0.8.0"
        val apacheCommonsIOVersion = "2.6"
    }
}

