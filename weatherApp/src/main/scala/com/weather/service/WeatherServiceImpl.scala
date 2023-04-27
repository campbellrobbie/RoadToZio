package com.weather.service

import com.weather.client.WeatherClient
import com.weather.domain.{Location, WeatherConfig}
import com.weather.http.HttpClient
import com.weather.service.http.{DeleteLocationResponse, GetLocationResponse, PutLocationResponse, WeatherService}
import zio._

final class WeatherServiceImpl(weatherClient: WeatherClient, locationCacheRef: Ref[Map[String, Location]], config: WeatherConfig) extends WeatherService[Task] {

  override def getLocation(name: String, lat: Double, lon: Double): Task[GetLocationResponse] = {

    // -> check for location in cache
    // if exists, return it
    // if doesn't exists, call weathermap API, add it to cache, then return it.

    locationCacheRef.get.map { cache =>
      cache.get(name) match {
        case Some(location) => location
        case None =>
          locationCacheRef.update(_.updated(name, Location(lat, lon)))
          for {
            location <- weatherClient.getLocation(lat, lon, config.apiKey)
            _ = locationCacheRef.update(_.updated(name, location))
          } yield location
      }
    }
  }


//    locationCacheRef.update(_.updated(name, Location(lat, lon)))
//    for {
//      response <- weatherClient.getLocation(lat, lon, config.apiKey)
//    } yield response


  override def putLocation(name: String, lat: Double, lon: Double): Task[PutLocationResponse] =
    locationCacheRef.update(_.updated(name, Location(lat, lon)))

  override def deleteLocation(name: String, lat: Double, lon: Double): Task[DeleteLocationResponse] =
    locationCacheRef.update(_.removed(name))

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