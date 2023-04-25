package de.htwg.se.schwimmen.cardStackComponent

import akka.http.scaladsl.server.Directives.*
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.*
import com.google.inject.{Guice, Injector}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.cardStackComponent.cardStackModul

@main def CardStackAPI() = {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "CardStackAPI")

  val injector: Injector = Guice.createInjector(new cardStackModul)
  var stack = injector.getInstance(classOf[CardStackInterface])
  stack = stack.setCardStack()

  val route =
    path("cardStack" / "threeCards") {
      get {
        val threeCards: String = stack.getThreeCardsInJsonFormat
        stack = stack.delThreeCards
        complete(HttpEntity(ContentTypes.`application/json`, threeCards))
      }
    }

  Http().newServerAt("127.0.0.1", 8080).bind(route)
  println(s"Server now online. Please navigate to http://localhost:8080/cardStack\n")

}
