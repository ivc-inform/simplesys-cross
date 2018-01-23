import sbt.Keys._
import sbt._
import sbt.{Credentials, Path}

object CommonSettings {
    object settingValues {

        val name = "cross-projects"
        val version = "1.0.0.5-SNAPSHOT"
        val scalaVersion = "2.12.4"
        val organization = "com.simplesys.cross"
        val scalacOptions = Seq(
            "-feature",
            "-language:higherKinds",
            "-language:implicitConversions",
            "-language:postfixOps",
            "-deprecation",
            "-unchecked")
    }


    val defaultSettings = {
        import sbt.Keys._
        Seq(
            version := settingValues.version,
            scalacOptions := settingValues.scalacOptions,
            organization := settingValues.organization
        )
    }

    val publishSettings = inThisBuild(Seq(
        publishMavenStyle := true,
        publishTo := {
            val corporateRepo = "http://toucan.simplesys.lan/"

            if (version.value.endsWith("-SNAPSHOT"))
                Some("snapshots" at corporateRepo + "artifactory/libs-snapshot-local")
            else
                Some("releases" at corporateRepo + "artifactory/libs-release-local")
        },
        credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
    ))

    val noPublishSettings = inThisBuild(Seq(
        publishArtifact := false,
        packagedArtifacts := Map.empty,
        publish := {},
        publishLocal := {}
    ))
}
