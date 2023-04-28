package com.weather.service

import com.weather.client.WeatherClient
import com.weather.domain.{Location, WeatherConfig}
import com.weather.service.http.{DeleteLocationResponse, GetLocationResponse, PutLocationResponse, WeatherService}
import zio._

final class WeatherServiceImpl(weatherClient: WeatherClient, locationCacheRef: Ref[Map[String, Location]], config: WeatherConfig) extends WeatherService[Task] {

  override def getLocation(name: String, lat: Double, lon: Double): Task[GetLocationResponse] = {

    for {
      location <- weatherClient.getLocation(lat, lon, config.apiKey)
      _        = locationCacheRef.update(_.updated(name, location))
      response = GetLocationResponse(lat, lon, name)
    } yield response

  }

  override def putLocation(name: String, lat: Double, lon: Double): Task[PutLocationResponse] =
    for {
      _ <- locationCacheRef.update(_.updated(name, Location(lat, lon)))
      response = PutLocationResponse()
    } yield response

  override def deleteLocation(name: String, lat: Double, lon: Double): Task[DeleteLocationResponse] =
    for {
      _        <- locationCacheRef.update(_.removed(name))
      response = DeleteLocationResponse()
    } yield response

}

object WeatherServiceImpl {

  val live =
    ZLayer(
      for {
        client         <- ZIO.service[WeatherClient]
        config         <- ZIO.config(WeatherConfig.config)
        locationsCache <- Ref.make(Map.empty[String, Location])
      } yield new WeatherServiceImpl(client, locationsCache, config)
    )

}