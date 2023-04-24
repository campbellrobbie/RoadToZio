package com.weather.service

import com.weather.client.WeatherClient
import com.weather.domain.{Location, WeatherConfig}
import com.weather.http.HttpClient
import com.weather.service.http.{DeleteLocationResponse, GetLocationResponse, PutLocationResponse, WeatherService}
import zio._

final class WeatherServiceImpl(weatherClient: WeatherClient, locationCache: Ref[Map[String, Location]], config: WeatherConfig) extends WeatherService[Task] {

  override def getLocation(name: String, lat: Double, lon: Double): Task[GetLocationResponse] = {
    locationCache.get.map(_.get(name).fold(ApiCallGoesHere)(x => x))

    val maybeLocationFromCache = locationCache.get.map(_.get(name))

    locationCache.update(_.updated(name, Location(lat, lon)))
    for {
      response <- weatherClient.getLocation(lat, lon, config.apiKey)
    } yield response
  }

  override def putLocation(name: String, lat: Double, lon: Double): Task[PutLocationResponse] =
    locationCache.update(_.updated(name, Location(lat, lon)))

  override def deleteLocation(name: String, lat: Double, lon: Double): Task[DeleteLocationResponse] =
    locationCache.update(_.removed(name))

}

object WeatherServiceImpl {

  val live: URLayer[HttpClient, WeatherServiceImpl with WeatherConfig] =
    ZLayer(
      for {
        client         <- ZIO.service[WeatherClient]
        config         <- ZIO.config(WeatherConfig.config)
        locationsCache <- Ref.make(Map.empty[String, Location])
      } yield new WeatherServiceImpl(client, locationsCache, config)
    )

}