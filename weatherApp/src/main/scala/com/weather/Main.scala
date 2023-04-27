package com.weather

import com.weather.http.{HttpClient, HttpServer}
import com.weather.service.WeatherServiceImpl
import zio._
object Main extends ZIOAppDefault {

  override def run = ZIO.scoped {
    val service = for {
      weatherService <- ZIO.service[WeatherServiceImpl]
    } yield weatherService

    service.provide(HttpServer.build(), WeatherServiceImpl.live, HttpClient.live)

  }
}
