package de.htwg.se.schwimmen.cardStackComponent

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import de.htwg.se.schwimmen.cardStackComponent.cardStackImpl.CardStackDAO

//import akka.http.scaladsl.server.Directives.*
//import akka.actor.typed.ActorSystem
//import akka.actor.typed.scaladsl.Behaviors
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.server.Directives.*
//import akka.http.scaladsl.model.*
import com.google.inject.{Guice, Injector}

// import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.ExecutionContext.Implicits.global

import scala.io.StdIn
import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.cardStackComponent.cardStackModul
import play.api.libs.json.{JsObject, Json}

object CardStackAPI {
  // implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "CardStackAPI")

  private var server: Option[Http.ServerBinding] = None

  given system: ActorSystem = ActorSystem("DiceService")

  @main def main(): Unit = {

    val injector: Injector = Guice.createInjector(new cardStackModul)
    var stack = injector.getInstance(classOf[CardStackInterface])
    stack = stack.setCardStack()

    val route =
      concat(
        path("cardStack" / "threeCards") {
          get {
            val threeCardVonDB = CardStackDAO.getThreeCards()
            val threeCards: JsObject = Json.obj(
              "firstCard" -> Json.obj(
                "Value" -> threeCardVonDB.head._2,
                "Color" -> threeCardVonDB.head._3,
              ),
              "secondCard" -> Json.obj(
                "Value" -> threeCardVonDB(1)._2,
                "Color" -> threeCardVonDB(1)._3,
              ),
              "thirdCard" -> Json.obj(
                "Value" -> threeCardVonDB.last._2,
                "Color" -> threeCardVonDB.last._3,
              )
            )
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "data" -> threeCards
            ).toString))
          }
        },
        path("test") {
          get {
            complete("OK")
          }
        }
      )

    val server = Some(Http().newServerAt("0.0.0.0", 8080).bind(route))
    server.get.map { _ =>
      println(s"Server now online. Please navigate to http://localhost:8080/cardStack\n")
    } recover { case ex =>
      println(s"Server could not start: ${ex.getMessage}")
    }
    //  Http().newServerAt("0.0.0.0", 8080).bind(route)
    //  println(s"Server now online. Please navigate to http://localhost:8080/cardStack\n")

  }
}
