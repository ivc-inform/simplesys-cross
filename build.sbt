import sbtcrossproject.{CrossType, crossProject}

lazy val root = crossProject(JSPlatform, JVMPlatform)
  .settings(CommonSettings.noPublishSettings)
  .settings(
      inThisBuild(Seq(
          scalaVersion := CommonSettings.settingValues.scalaVersion,
          scalacOptions := CommonSettings.settingValues.scalacOptions,
          organization := CommonSettings.settingValues.organization,
          name := CommonSettings.settingValues.name
      ) ++ CommonSettings.defaultSettings)
  )
  .aggregate(circeExtender)
  .dependsOn(circeExtender)

lazy val circeExtender = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .settings(CommonSettings.publishSettings)
  .settings(
      name := "circe-extender"
  )
  .settings(CommonSettings.defaultSettings)
  .jvmSettings(
      libraryDependencies ++= Seq(
          CommonDeps.scalaTest
      )
  )
  .jsSettings(
      crossTarget in fastOptJS := (sourceDirectory in Compile).value / "javascriptJS",
      crossTarget in fullOptJS := (sourceDirectory in Compile).value / "javascriptJS",
      crossTarget in packageJSDependencies := (sourceDirectory in Compile).value / "javascriptJS",
      libraryDependencies ++= Seq(
          "org.scalatest" %%% "scalatest" % "3.0.4" % Test
      ),
      scalacOptions ++= {
          if (scalaJSVersion.startsWith("0.6."))
              Seq("-P:scalajs:sjsDefinedByDefault")
          else
              Nil
      }
  )

lazy val circeExtenderJS = circeExtender.js
lazy val circeExtenderJVM = circeExtender.jvm





