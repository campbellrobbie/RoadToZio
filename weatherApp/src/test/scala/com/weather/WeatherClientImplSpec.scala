package com.weather

import com.weather.client.WeatherClient
import com.weather.domain.{Location, WeatherConfig}
import com.weather.service.WeatherServiceImpl
import org.scalatest.EitherValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import zio._

final class WeatherClientImplSpec extends AnyWordSpec with Matchers with EitherValues {

  private val emptyCache = Ref.make(Map.empty[String, Location])
  private val weatherService = new WeatherServiceImpl(WeatherClient, emptyCache, WeatherConfig.config)

  "WeatherService" when {
    "getLocation" should {
      "succesfully return a location when provided coordinates and a name." in {




      }
    }

  }



}