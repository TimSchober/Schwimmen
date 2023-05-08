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
import play.api.libs.json.*

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

@main def PlayerAPI(): Unit = {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "PlayerAPI")

  val injector: Injector = Guice.createInjector(new playerModul)
  var playerAmount: Int = 0
  var players: List[PlayerInterface] = List()
  var field: PlayingFieldInterface = injector.getInstance(classOf[PlayingFieldInterface])

  val route =
    concat(
      path("playersAndPlayingfield" / "reset") {
        get {
          playerAmount = 0
          players = List()
          field = injector.getInstance(classOf[PlayingFieldInterface])
          println("game is reset")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> "game is reset"
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "players" / "changeToNextPlayer") {
        get {
          println(players)
          players = players :+ players.head
          players = players.drop(1)
          println(players)
          println(s"It's now the turn of ${players.head.name.toString}")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"It's now the turn of ${players.head.name.toString}"
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "players" / "getCurrentPlayer") {
        get {
          println(s"Data of ${players.head.name.toString} was sent")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"Data of ${players.head.name.toString} was sent",
            "data" -> players.head.playerDataToJsonObject
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "players" / "playeramount") {
        concat(
          get {
            val JsonObject = Json.obj(
              "success" -> true,
              "playeramount" -> playerAmount.toString
            )
            println(s"playeramount is set to ${playerAmount.toString}")
            complete(HttpEntity(ContentTypes.`application/json`, JsonObject.toString))
          },
          post {
            entity(as[String]) { request =>
              val json = Json.parse(request)
              val amount = (json \ "playerAmount").get.toString.replaceAll("\"", "").toInt
              if (field.processPlayerAmount(amount)) {
                playerAmount = amount
                println(s"player amount set to ${playerAmount.toString}")
                complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
                  "success" -> true,
                  "reason" -> s"player amount set to ${playerAmount.toString}"
                ).toString))
              } else {
                println(s"invalid player amount")
                complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
                  "success" -> false,
                  "reason" -> s"invalid player amount"
                ).toString))
              }
            }
          }
        )
      },
      path("playersAndPlayingfield" / "playingField" / "cardsOnPlayingField") {
        concat(
          post {
            entity(as[String]) { request =>
              val json = Json.parse(request)
              val fieldFirstCard: (String, String) = (
                (json \ "cardsOnField" \ "firstCard" \ "Value").get.toString.replaceAll("\"", ""),
                (json \ "cardsOnField" \ "firstCard" \ "Color").get.toString.replaceAll("\"", ""))
              val fieldSecondCard: (String, String) = (
                (json \ "cardsOnField" \ "secondCard" \ "Value").get.toString.replaceAll("\"", ""),
                (json \ "cardsOnField" \ "secondCard" \ "Color").get.toString.replaceAll("\"", ""))
              val fieldThirdCard: (String, String) = (
                (json \ "cardsOnField" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", ""),
                (json \ "cardsOnField" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", ""))
              val list: List[(String, String)] = List(fieldFirstCard, fieldSecondCard, fieldThirdCard)
              field = field.setCardsOnField(list)
              println(s"field was updated")
              complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
                "success" -> true,
                "reason" -> s"field was updated"
              ).toString))
            }
          },
          get {
            println(s"field was sent")
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "reason" -> s"field was sent",
              "data" -> field.fieldDataToJsonObject
            ).toString))
          }
        )
      },
      path("playersAndPlayingfield" / "players" / "setCurrentPlayerToKnocked") {
        get {
          players.head.setHasKnocked(true)
          println(s"set current player has knocked = true")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"set current player has knocked = true"
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "swapAllCards") {
        get {
          val newFieldCards = players.head.cardsOnHand
          val newPlayerCards = field.cardsOnField
          players.head.setCardsOnHand(newPlayerCards)
          field.setCardsOnField(newFieldCards)
          println(s"all cards of current player and field where changed")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"all cards of current player and field where changed"
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "swapOneCards") {
        post {
          entity(as[String]) { request =>
            val json = Json.parse(request)
            val playerCard = (json \ "indexplayer").get.toString.replaceAll("\"", "").toInt
            val fieldCard = (json \ "indexfield").get.toString.replaceAll("\"", "").toInt
            val i = players.head.swapCard(field, playerCard, fieldCard)
            val j = field.swapCard(players.head, playerCard, fieldCard)
            players = players.patch(0, Seq(players.head.setCardsOnHand(i)), 1)
            field = field.setCardsOnField(j)
            println(s"one card of current player and field was changed")
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "reason" -> s"one card of current player and field was changed"
            ).toString))
          }
        }
      },
      path("playersAndPlayingfield" / "players" / "playeradd") {
        post {
          entity(as[String]) { request =>
            val json = Json.parse(request)
            val playerName = (json \ "name").get.toString.replaceAll("\"", "")
            val playerFirstCard: (String, String) = (
              (json \ "cardsOnHand" \ "firstCard" \ "Value").get.toString.replaceAll("\"", ""),
              (json \ "cardsOnHand" \ "firstCard" \ "Color").get.toString.replaceAll("\"", ""))
            val playerSecondCard: (String, String) = (
              (json \ "cardsOnHand" \ "secondCard" \ "Value").get.toString.replaceAll("\"", ""),
              (json \ "cardsOnHand" \ "secondCard" \ "Color").get.toString.replaceAll("\"", ""))
            val playerThirdCard: (String, String) = (
              (json \ "cardsOnHand" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", ""),
              (json \ "cardsOnHand" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", ""))
            val list: List[(String, String)] = List(playerFirstCard, playerSecondCard, playerThirdCard)
            val player = injector.getInstance(classOf[PlayerInterface])
            val playerWithNameAndCards = player.setName(playerName).setCardsOnHand(list)
            players = players :+ playerWithNameAndCards
            println(s"player with name $playerName was added")
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "reason" -> s"player with name $playerName was added"
            ).toString))
          }
        }
      }
    )

  Http().newServerAt("127.0.0.1", 8081).bind(route)
  println(s"Server now online. Please navigate to http://localhost:8081/playersAndPlayingfield\n")

}
