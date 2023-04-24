package com.weather.http

import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{EntityDecoder, Request}
import zio.interop.catz._
import zio.{Task, TaskLayer, ZLayer}

trait HttpClient {

  def makeRequest[A](request: Request[Task])(implicit d: EntityDecoder[Task, A]): Task[A]

}

object HttpClient {

  val live: TaskLayer[HttpClientImpl] =
    ZLayer.scoped(
      for {
        client <- EmberClientBuilder.default[Task].build.toScopedZIO
      } yield new HttpClientImpl(client)
    )

 private class HttpClientImpl(client: Client[Task]) extends HttpClient {

   override def makeRequest[A](request: Request[Task])(implicit d: EntityDecoder[Task, A]): Task[A] = client.expect[A](request)

 }

}
