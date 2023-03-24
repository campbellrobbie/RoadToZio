Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version := "local"
ThisBuild / scalaVersion := "2.13.9"

val http4sVersion              = "0.23.16"
val circeVersion               = "0.14.3"

lazy val weatherApp = (project in file("weatherApp"))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.10",
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "io.circe" %% "circe-core"    % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )
  )

lazy val `road-to-zio` = (project in file(".")).aggregate(weatherApp)
