package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

import akka.http.scaladsl.server.Directives.{complete, concat, get, path}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode}
import akka.http.scaladsl.server.{ExceptionHandler, Route}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

@main def CardStackAPI() = {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "akka-http")

  val route = get {
    path("test") {
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "test"))
    }
  }

  Http().newServerAt("127.0.0.1", 8080).bind(route)

}
