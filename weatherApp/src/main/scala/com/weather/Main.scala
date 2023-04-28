package com.weather

import com.weather.client.WeatherClient
import com.weather.http.{HttpClient, HttpServer}
import com.weather.service.WeatherServiceImpl
import zio._
object Main extends ZIOAppDefault {

  override def run = ZIO.scoped {
    val service = for {
      weatherService <- ZIO.service[WeatherServiceImpl]
    } yield weatherService

    service.provide(WeatherServiceImpl.live, HttpClient.live, WeatherClient.httpLayer)

  }
}
