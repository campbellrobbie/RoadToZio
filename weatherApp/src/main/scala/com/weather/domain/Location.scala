package com.weather.domain

import org.http4s.EntityDecoder
import zio.Task

final case class Location(lat: Double, lon: Double)

object Location {

  implicit val decoder: EntityDecoder[Task, Location] = ???

}
