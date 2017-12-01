import sbt.Keys._
import sbt.{Def, _}

object PluginDeps {
    object versions {

    }
}

object CommonDeps {
    val scalaXml = "org.scala-lang.modules" %% "scala-xml" % versions.scalaModulesVersion
    val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % versions.scalaParserCombinatorsVersion
    val scalaReflect = Def.setting("org.scala-lang" % "scala-reflect" % scalaVersion.value)
    val logbackClassic = "ch.qos.logback" % "logback-classic" % versions.logbackVersion
    val logbackAccess = "ch.qos.logback" % "logback-access" % versions.logbackVersion
    val logbackCore = "ch.qos.logback" % "logback-core" % versions.logbackVersion

    val javaxTransaction = "javax.transaction" % "jta" % versions.javaxTransactionVersion
    val servletAPI = "javax.servlet" % "javax.servlet-api" % versions.servletAPIVersion

    val akkaActor = "com.typesafe.akka" %% "akka-actor" % versions.akkaVersion
    val akkaSLF4J = "com.typesafe.akka" %% "akka-slf4j" % versions.akkaVersion
    val akkaAgent = "com.typesafe.akka" %% "akka-agent" % versions.akkaVersion
    val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % versions.akkaVersion % Test

    val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.18.2-SNAPSHOT"
    val scalaTest = "org.scalatest" %% "scalatest" % versions.scalaTestVersion % Test
    
    object versions {
        val scalaModulesVersion = "1.0.6"

        val akkaVersion = "2.5.7"

        val scalazVersion = "7.2.8"

        val doobieVersion = "0.4.1"

        val logbackVersion = "1.2.3"
                                                                                                                                             
        val apacheCommonsLangVersion = "3.4"

        val javaxTransactionVersion = "1.1"
        val servletAPIVersion = "3.1.0"

        val wrappedSaxonEEVersion = "9.5.1.2-1"
        val xercesVersion = "2.11.0"
        val hikariCPVersionVersion = "2.7.1"
        val slickVersion = "3.3.0-SNAPSHOT"

        val configTypesafeVersion = "1.3.1"

        val liquibaseWrapped = "3.0.2"

        val h2DBVersion = "1.4.193"
        val derbyDBVersion = "10.11.1.1"
        val postgresDriverVersion = "9.4-1201-jdbc41"
        val oracle12DriverVersion = "12.2.0.1"

        val uTestVersion = "0.3.1"
        val scalaTestVersion = "3.0.3"
        val scalaMetaVersion = "2.0.1"
        val scalaArmVersion = "2.0"
        val scalaParserCombinatorsVersion = "1.0.5"
        val scalaSpecsVersion = "3.8.6"
        val sbtJUnitVersion = "0.11"
        val mockitoVersion = "1.9.5"

        val rhinoVersion = "1.7.7.1"

        val apacheCommonsIOVersion = "2.5"

        val scalaRiformVersion = "0.1.8"

        val utilEvalVersion = "6.43.0"
        val configWrapperVersion = "0.4.4"

        val circeVersion = "0.9.0-M2"
    }
}

