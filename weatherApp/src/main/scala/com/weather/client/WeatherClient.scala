package com.weather.client

import com.weather.domain.Location
import com.weather.http.HttpClient
import org.http4s.Method.GET
import org.http4s.{Request, Uri}
import zio.{Task, ZLayer}

trait WeatherClient {

  def getLocation(lat: Double, lon: Double, apiKey: String): Task[Location]

}

object WeatherClient {

  val httpLayer: ZLayer[HttpClient, Nothing, WeatherClient] = ZLayer.fromFunction(new WeatherClientImpl(_))

}

private final class WeatherClientImpl(httpClient: HttpClient) extends WeatherClient {

  private val baseUri = "https://api.openweathermap.org/data/2.5/weather"

  override def getLocation(lat: Double, lon: Double, apiKey: String): Task[Location] =
    httpClient.makeRequest(buildGetLocationRequest(lat, lon, apiKey))

  def buildGetLocationRequest(lat: Double, lon: Double, apiKey: String): Request[Task] =
    Request[Task](GET, Uri(path = Uri.Path.unsafeFromString(baseUri)))
      .withQueryParam("lat", lat)
      .withQueryParam("lon", lon)
      .withQueryParam("appid", apiKey)

}
