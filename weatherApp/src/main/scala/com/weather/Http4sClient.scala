package com.weather

import io.circe.Decoder
import zio._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.Client
import zio.interop.catz._

object Http4sClient {
  type HttpClient = WeatherService

  def http4s: ZLayer[Client[Task], Nothing, HttpClient] = ???

  trait WeatherService {
    protected val rootUrl = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}"

    def get[T](uri: String, parameters: Map[String, String])
              (implicit d: Decoder[T]): Task[T]
  }
}