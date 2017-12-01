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
  .aggregate(`circe-extender`, `common-cross`, `servlet-wrapper`)
  .dependsOn(`circe-extender`, `common-cross`, `servlet-wrapper`)

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
  .dependsOn(`common-cross`)

lazy val `circe-extenderJS` = `circe-extender`.js
lazy val `circe-extenderJVM` = `circe-extender`.jvm

lazy val `common-cross` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .settings(CommonSettings.publishSettings)
  .settings(
      name := "common-cross"
  )
  .settings(CommonSettings.defaultSettings)
  .jvmSettings(
      libraryDependencies ++= Seq(
          CommonDeps.apacheCommonsLang,
          CommonDeps.apacheCommonsIO,
          CommonDeps.scalaXml,
          CommonDeps.scalaReflect.value,
          CommonDeps.scalaLogging,
          CommonDeps.scalaTest
      )
  )
  .jsSettings(scalajSCommonOption)

lazy val `common-crossJS` = `common-cross`.js
lazy val `common-crossJVM` = `common-cross`.jvm

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





