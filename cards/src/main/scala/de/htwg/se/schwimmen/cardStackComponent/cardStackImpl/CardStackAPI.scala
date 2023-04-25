package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl
import de.htwg.se.schwimmen.cardStackComponent.cardStackImpl.CardStack

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.*
import scala.io.StdIn
import play.api.libs.json._

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object CardStackAPI {
  // needed to run the route
  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  val executionContext: ExecutionContextExecutor = system.executionContext

  given ExecutionContextExecutor = executionContext

  var cardStack: CardStack = CardStack()
  cardStack = cardStack.setCardStack()
  val json: JsValue = Json.toJson(cardStack.getThreeCards)

  val route = concat(
    path("CardStack" / "getThreeCards") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, json))
      }
    }
  )

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

  println(s"Server now online. Please navigate to http://localhost:8080/fileio\nPress RETURN to stop...")

}
