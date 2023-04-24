package com.weather.domain

import zio.Config

object WeatherConfig {
  val config: Config[WeatherConfig] =
    Config.string("apiKey").map(WeatherConfig.apply)
}

final case class WeatherConfig(apiKey: String)