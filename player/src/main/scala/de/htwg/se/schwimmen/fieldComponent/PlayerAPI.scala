package de.htwg.se.schwimmen.fieldComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.fieldComponent.PlayingFieldInterface
import de.htwg.se.schwimmen.fieldComponent.PlayerInterface
import de.htwg.se.schwimmen.fieldComponent.fieldImpl.PlayerDAO
import de.htwg.se.schwimmen.fieldComponent.fieldImpl.PlayingFieldDAO
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
  var currentPlayer: String = ""

  val route =
    concat(
      path("test") {
        get {
          complete("OK")
        }
      },
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
          var currPlayer = PlayerDAO.getFirstPlayer()
          if (currentPlayer == "") {
            currPlayer = PlayerDAO.getFirstPlayer()
          } else {
            currPlayer = PlayerDAO.getnextPlayer(currentPlayer)
          }
          currentPlayer = currPlayer.head._2
          println(s"It's now the turn of ${currentPlayer}")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"It's now the turn of ${currentPlayer}"
          ).toString))
        }
      },
      path("playersAndPlayingfield" / "players" / "getCurrentPlayer") {
        get {
          var currPlayer: Seq[(Long, String, Int, String, String, String, String, String, String, String)] = null
          if (currentPlayer == "") {
            currPlayer = PlayerDAO.getCurrentPlayer(PlayerDAO.getFirstPlayer().head._2)
          } else {
            currPlayer = PlayerDAO.getnextPlayer(currentPlayer)
          }

          currentPlayer = currPlayer.head._2
          println(s"Data of ${currentPlayer} was sent")
          complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
            "success" -> true,
            "reason" -> s"Data of ${currentPlayer} was sent",
            "data" -> Json.obj(
              "name" -> currentPlayer,
              "hasKnocked" -> currPlayer.head._4,
              "life" -> currPlayer.head._3,
              "cardsOnHand" -> Json.obj(
                "firstCard" -> Json.obj(
                  "Value" -> currPlayer.head._5,
                  "Color" -> currPlayer.head._6
                ),
                "secondCard" -> Json.obj(
                  "Value" -> currPlayer.head._7,
                  "Color" -> currPlayer.head._8
                ),
                "thirdCard" -> Json.obj(
                  "Value" -> currPlayer.head._9,
                  "Color" -> currPlayer.head._10
                )
              )
            )
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
            // einfügen in fieldtable
            entity(as[String]) { request =>
              val json = Json.parse(request)
              val ffcv = (json \ "cardsOnField" \ "firstCard" \ "Value").get.toString.replaceAll("\"", "")
              val ffcc = (json \ "cardsOnField" \ "firstCard" \ "Color").get.toString.replaceAll("\"", "")
              val sfcv = (json \ "cardsOnField" \ "secondCard" \ "Value").get.toString.replaceAll("\"", "")
              val sfcc = (json \ "cardsOnField" \ "secondCard" \ "Color").get.toString.replaceAll("\"", "")
              val tfcv = (json \ "cardsOnField" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", "")
              val tfcc = (json \ "cardsOnField" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", "")
//              val fieldFirstCard: (String, String) = (ffcv, ffcc)
//              val fieldSecondCard: (String, String) = (sfcv, sfcc)
//              val fieldThirdCard: (String, String) = (tfcv, tfcc)
//              val list: List[(String, String)] = List(fieldFirstCard, fieldSecondCard, fieldThirdCard)
//              field = field.setCardsOnField(list)
              val id = 0L
              PlayingFieldDAO.insertFieldCards(id + 1L, ffcv, ffcc, sfcv, sfcc, tfcv, tfcc)
              println(s"field was updated")
              complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
                "success" -> true,
                "reason" -> s"field was updated"
              ).toString))
            }
          },
          get {
            // daten von field lesen
            val cardsOnField = PlayingFieldDAO.getFieldCards()
            println(s"field was sent")
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "reason" -> s"field was sent",
              "data" -> Json.obj(
                "cardsOnField" -> Json.obj(
                  "firstCard" -> Json.obj(
                    "Value" -> cardsOnField.head._2,
                    "Color" -> cardsOnField.head._3
                  ),
                  "secondCard" -> Json.obj(
                    "Value" -> cardsOnField.head._4,
                    "Color" -> cardsOnField.head._5
                  ),
                  "thirdCard" -> Json.obj(
                    "Value" -> cardsOnField.head._6,
                    "Color" -> cardsOnField.head._7
                  )
                )
              )
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
            val fcv = (json \ "cardsOnHand" \ "firstCard" \ "Value").get.toString.replaceAll("\"", "")
            val fcc = (json \ "cardsOnHand" \ "firstCard" \ "Color").get.toString.replaceAll("\"", "")
            val scv = (json \ "cardsOnHand" \ "secondCard" \ "Value").get.toString.replaceAll("\"", "")
            val scc = (json \ "cardsOnHand" \ "secondCard" \ "Color").get.toString.replaceAll("\"", "")
            val tcv = (json \ "cardsOnHand" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", "")
            val tcc = (json \ "cardsOnHand" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", "")
//            val playerFirstCard: (String, String) = (fcv, fcc)
//            val playerSecondCard: (String, String) = (scv, scc)
//            val playerThirdCard: (String, String) = (tcv, tcc)
//            val list: List[(String, String)] = List(playerFirstCard, playerSecondCard, playerThirdCard)
//            val player = injector.getInstance(classOf[PlayerInterface])
//            val playerWithNameAndCards = player.setName(playerName).setCardsOnHand(list)
//            players = players :+ playerWithNameAndCards
            val playerId = 0L
            PlayerDAO.insertPlayer(playerId, playerName, 3, "false", fcv, fcc, scv, scc, tcv, tcc)
            println(s"player with name $playerName was added")
            complete(HttpEntity(ContentTypes.`application/json`, Json.obj(
              "success" -> true,
              "reason" -> s"player with name $playerName was added"
            ).toString))
          }
        }
      }
    )

  Http().newServerAt("0.0.0.0", 8081).bind(route)
  println(s"Server now online. Please navigate to http://localhost:8081/playersAndPlayingfield\n")
  StdIn.readLine()
}
