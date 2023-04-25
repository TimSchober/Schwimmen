package de.htwg.se.schwimmen.fieldComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.fieldComponent.PlayingFieldInterface
import de.htwg.se.schwimmen.fieldComponent.PlayerInterface
import de.htwg.se.schwimmen.playerModul

import play.api.libs.json._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

@main def PlayerAPI() = {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "PlayerAPI")

  val injector: Injector = Guice.createInjector(new playerModul)
  var playerAmount: Int = 0
  var players: List[PlayerInterface] = List()

  val route =
    path("playersAndPlayingfield" / "players" / "playeramount") {
      post {
        entity(as[String]) { request =>
          val json = Json.parse(request)
          playerAmount = (json \ "playerAmount").get.toString.toInt
          complete("player amount set to " + playerAmount.toString)
        }
      }
    }
    path("playersAndPlayingfield" / "players" / "playeradd") {
      post {
        entity(as[String]) { request =>
          val json = Json.parse(request)
          val playerName = (json \ "name").get.toString
          val playerFirstCard: (String, String) = ((json \ "cardsOnHand" \ "firstCard" \ "Value").get.toString,
            (json \ "cardsOnHand" \ "firstCard" \ "Color").get.toString)
          val playerSecondCard: (String, String) = ((json \ "cardsOnHand" \ "secondCard" \ "Value").get.toString,
            (json \ "cardsOnHand" \ "secondCard" \ "Color").get.toString)
          val playerThirdCard: (String, String) = ((json \ "cardsOnHand" \ "thirdCard" \ "Value").get.toString,
            (json \ "cardsOnHand" \ "thirdCard" \ "Color").get.toString)
          val list: List[(String, String)] = List(playerFirstCard, playerSecondCard, playerThirdCard)
          var player = injector.getInstance(classOf[PlayerInterface])
          player = player.setName(playerName)
          player = player.setCardsOnHand(list)
          players = players :+ player
          complete("player with name " + playerName + " was added")
        }
      }
    }

  Http().newServerAt("127.0.0.1", 8080).bind(route)
  println(s"Server now online. Please navigate to http://localhost:8080/playersAndPlayingfield/players/playeramount\n")

}
