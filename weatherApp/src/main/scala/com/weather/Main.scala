package com.weather

import zio._
import zio.Console._

import java.io.IOException

object Main extends ZIOAppDefault {

  def run: ZIO[Any, IOException, Unit] = fetchWeatherData

  val fetchWeatherData: ZIO[Any, IOException, Unit] =
    for {
      _ <- printLine("Hello! Shall we implement some code to fetch weather data????")
      name <- readLine
      _ <- printLine(s"kewl beans ")
    } yield ()

}
