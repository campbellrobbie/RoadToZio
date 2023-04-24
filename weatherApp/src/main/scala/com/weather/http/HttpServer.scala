package com.weather.http

import com.comcast.ip4s.Port
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import zio.interop.catz._
import zio.{Task, ZLayer}

object HttpServer {

  def build(): ZLayer[Any, Throwable, Server] = ZLayer.scoped(
    EmberServerBuilder
      .default[Task]
      .withPort(Port.fromInt(8026).get)
      .build
      .toScopedZIO
  )

}
