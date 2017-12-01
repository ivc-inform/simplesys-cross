import CommonDeps.versions
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object CommonDepsScalaJS {

    val scalaTest = Def.setting("org.scalatest" %%% "scalatest" % "3.0.4" % Test)

    val circeCore = Def.setting("io.circe" %%% "circe-core" % versions.circeVersion)
    val circeGeneric = Def.setting("io.circe" %%% "circe-generic" % versions.circeVersion)
    val circeParser = Def.setting("io.circe" %%% "circe-parser" % versions.circeVersion)

}
