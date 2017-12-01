import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object CommonDepsScalaJS {

    val scalaTest = Def.setting("org.scalatest" %%% "scalatest" % "3.0.4" % Test)

}
