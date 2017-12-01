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
  .aggregate(`circe-extender`, `servlet-wrapper`)
  .dependsOn(`circe-extender`, `servlet-wrapper`)

val scalajSCommonOption = Seq(
    //      crossTarget in fastOptJS := (sourceDirectory in Compile).value / "javascriptJS",
    //      crossTarget in fullOptJS := (sourceDirectory in Compile).value / "javascriptJS",
    //      crossTarget in packageJSDependencies := (sourceDirectory in Compile).value / "javascriptJS",
    libraryDependencies ++= Seq(
        CommonDepsScalaJS.scalaTest.value
    ),
    scalacOptions ++= {
        if (scalaJSVersion.startsWith("0.6."))
            Seq("-P:scalajs:sjsDefinedByDefault")
        else
            Nil
    }
)

lazy val `circe-extender` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .settings(CommonSettings.publishSettings)
  .settings(
      name := "circe-extender",
      libraryDependencies ++= Seq(
          CommonDeps.akkaHttpCirce,
          CommonDeps.scalaTest
      )
  )
  .settings(CommonSettings.defaultSettings)
  .jvmSettings(
      libraryDependencies ++= Seq(
          CommonDeps.akkaHttpCirce,
          CommonDeps.scalaTest
      )
  )
  .jsSettings(scalajSCommonOption)
  .jsSettings(
      libraryDependencies ++= Seq(
          CommonDepsScalaJS.circeCore.value,
          CommonDepsScalaJS.circeGeneric.value,
          CommonDepsScalaJS.circeParser.value,
      )
  )

lazy val `circe-extenderJS` = `circe-extender`.js
lazy val `circe-extenderJVM` = `circe-extender`.jvm

lazy val `servlet-wrapper` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .settings(CommonSettings.publishSettings)
  .settings(
      name := "servlet-wrapper"
  )
  .settings(CommonSettings.defaultSettings)
  .jvmSettings(
      libraryDependencies ++= Seq(
          CommonDeps.scalaLogging,
          CommonDeps.scalaTest
      )
  )
  .jsSettings(scalajSCommonOption)
  .dependsOn(`circe-extender`)

lazy val `servlet-wrapperJS` = `servlet-wrapper`.js
lazy val `servlet-wrapperJVM` = `servlet-wrapper`.jvm





