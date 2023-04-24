Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version := "local"
ThisBuild / scalaVersion := "2.13.9"

val http4sVersion       = "0.23.16"
val circeVersion        = "0.14.3"
val zioInteropCats      = "23.0.0.2"
val zioConfig           = "3.0.7"
val smithy4s            = "0.17.2"

lazy val weatherApp = (project in file("weatherApp"))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.10",
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "io.circe" %% "circe-core"    % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "dev.zio" %% "zio-interop-cats" % zioInteropCats,
      "dev.zio" %% "zio-config"       % zioConfig,
      "dev.zio" %% "zio-config-typesafe" % zioConfig,
      "dev.zio" %% "zio-config-magnolia" % zioConfig,
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4s,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4s
    )
  )

lazy val `road-to-zio` = (project in file(".")).aggregate(weatherApp)
