package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.inject.name.{Named, Names}
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.schwimmen.controller.controllerComponent.*
import net.codingwell.scalaguice.InjectorExtensions.*
import play.api.libs.json.{JsObject, JsValue, Json}
import scala.swing.event.Event

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller @Inject() () extends ControllerInterface with Publisher {

  val PlayerServer = "http://" + "localhost" + ":8081/playersAndPlayingfield" // localhost
  val StackServer = "http://" + "localhost" + ":8080/cardStack"

  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")

  val executionContext: ExecutionContextExecutor = system.executionContext

  given ExecutionContextExecutor = executionContext

  var playersize: Int = 0
  var playerAmount: Int = 0
  var lastPlayer: JsValue = Json.parse("{\"success\": false}")
  var headPlayer: JsValue = Json.parse("{\"success\": false}")
  var currentField: JsValue = Json.parse("{\"success\": false}")
  def updateData(e: Event): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/players/getCurrentPlayer"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              headPlayer = response
              val responseFuture2: Future[HttpResponse] = Http().singleRequest(HttpRequest(
                method = HttpMethods.GET,
                uri = PlayerServer + "/playingField/cardsOnPlayingField"
              ))
              responseFuture2.onComplete {
                case Failure(_)
                => sys.error("Failed getting Json")
                case Success(value)
                =>
                  Unmarshal(value.entity).to[String].onComplete {
                    case Failure(_) => sys.error("Failed unmarshalling")
                    case Success(value) =>
                      val response = Json.parse(value)
                      if ((response \ "success").get.toString.toBoolean) {
                        currentField = response
                        println((headPlayer \ "data" \ "name").get.toString)
                        publish(e)
                      } else {
                        println("updateData failed")
                      }
                  }
              }
            } else {
              println("updateData failed")
            }
        }
    }
  }

  def createNewGame(): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/reset"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              setupField()
            } else {
              println("createNewGame failed")
            }
        }
    }
  }

  def setupField(): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = StackServer + "/threeCards"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              val responseFuture2: Future[HttpResponse] = Http().singleRequest(HttpRequest(
                method = HttpMethods.POST,
                uri = PlayerServer + "/playingField/cardsOnPlayingField",
                entity = Json.obj(
                  "cardsOnField" -> Json.obj(
                    "firstCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "firstCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "firstCard" \ "Color").get.toString.replaceAll("\"", ""),
                    ),
                    "secondCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "secondCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "secondCard" \ "Color").get.toString.replaceAll("\"", ""),
                    ),
                    "thirdCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", ""),
                    )
                  )
                ).toString
              ))
              responseFuture2.onComplete {
                case Failure(_)
                => sys.error("Failed getting Json")
                case Success(value)
                =>
                  Unmarshal(value.entity).to[String].onComplete {
                    case Failure(_) => sys.error("Failed unmarshalling")
                    case Success(value) =>
                      val response = Json.parse(value)
                      if ((response \ "success").get.toString.toBoolean) {
                        publish(new NewGame)
                      } else {
                        println("setupField failed")
                      }
                  }
              }
            } else {
              println("setupField failed")
            }
        }
    }
  }

  def nextRound(): Unit = {
//    stack = injector.instance[CardStackInterface]
//    stack = stack.setCardStack()
//    field = injector.instance[PlayingFieldInterface]
//    field = field.setCardsOnField(stack.getThreeCards)
//    stack = stack.delThreeCards
//    fieldStack = Nil.::(field)
//    playerStack = Nil
//    var newPlayers: List[PlayerInterface] = Nil
//    players.foreach(pl => {
//      var newPlayer: PlayerInterface = injector.instance[PlayerInterface]
//      val playerName = pl.name match {
//        case Some(s) => s
//        case None => ""
//      }
//      newPlayer = newPlayer.setName(playerName)
//      newPlayer = newPlayer.setLife(pl.life)
//      newPlayer = newPlayer.setCardsOnHand(stack.getThreeCards)
//      stack = stack.delThreeCards
//      newPlayers = newPlayers :+ newPlayer
//    })
//    players = newPlayers
//    publish(new PlayerAdded)
  }

  def setPlayerAmount(plAm: Int): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.POST,
      uri = PlayerServer + "/players/playeramount",
      entity = Json.obj(
        "playerAmount" -> plAm.toString
      ).toString
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              playerAmount = plAm
              publish(PlayerAmountChanged(plAm))
            } else {
              println("setPlayerAmount failed")
            }
        }
    }
  }

  def addPlayer(name: String): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = StackServer + "/threeCards"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              val responseFuture2: Future[HttpResponse] = Http().singleRequest(HttpRequest(
                method = HttpMethods.POST,
                uri = PlayerServer + "/players/playeradd",
                entity = Json.obj(
                  "name" -> name,
                  "cardsOnHand" -> Json.obj(
                    "firstCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "firstCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "firstCard" \ "Color").get.toString.replaceAll("\"", ""),
                    ),
                    "secondCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "secondCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "secondCard" \ "Color").get.toString.replaceAll("\"", ""),
                    ),
                    "thirdCard" -> Json.obj(
                      "Value" -> (response \ "data" \ "thirdCard" \ "Value").get.toString.replaceAll("\"", ""),
                      "Color" -> (response \ "data" \ "thirdCard" \ "Color").get.toString.replaceAll("\"", ""),
                    )
                  )
                ).toString
              ))
              responseFuture2.onComplete {
                case Failure(_)
                => sys.error("Failed getting Json")
                case Success(value)
                =>
                  Unmarshal(value.entity).to[String].onComplete {
                    case Failure(_) => sys.error("Failed unmarshalling")
                    case Success(value) =>
                      val response = Json.parse(value)
                      if ((response \ "success").get.toString.toBoolean) {
                        playersize = playersize + 1
                        updateData(new PlayerAdded)
                      } else {
                        println("addPlayer failed")
                      }
                  }
              }
            } else {
              println("addPlayer failed")
            }
        }
    }
  }

  def yesSelected(): Boolean = {
    publish(new YesSelected)
    true
  }
  def cardSelected(): Boolean = {
    publish(new CardSelected)
    true
  }

  var indexStack: List[Int] = List[Int]()
  def swapCards(indexplayer: Int, indexfield: Int): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.POST,
      uri = PlayerServer + "/swapOneCards",
      entity = Json.obj(
        "indexplayer" -> indexplayer,
        "indexfield" -> indexfield
      ).toString
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              publish(new CardsChanged)
            } else {
              println("swapCards failed")
            }
        }
    }
  }

  def swapAllCards(): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/playersAndPlayingfield/swapAllCards"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              updateData(new CardsChanged)
            } else {
              println("swapAllCards failed")
            }
        }
    }
  }

  def nextPlayer(): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/players/changeToNextPlayer"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              lastPlayer = headPlayer
              updateData(new PlayerChanged)
            } else {
              println("nextPlayer failed")
            }
        }
    }
  }

  def setKnocked(): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/players/setCurrentPlayerToKnocked"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              updateData(new KnockedChanged)
            } else {
              println("setKnocked failed")
            }
        }
    }
  }

  def doNothing(): Unit = {
//    playerStack = playerStack.::(players.head)
//    fieldStack = fieldStack.::(field)
//    undoManager.doStep(new DoNothingCommand( this))
  }

  def undoStep(): Unit = {
//    players = players.reverse :+ playerStack.head
//    players = players.drop(1)
//    players = players.reverse
//    playerStack = playerStack.drop(1)
//    field = fieldStack.head
//    fieldStack = fieldStack.drop(1)
  }

  def undo(): Unit = {
//    undoManager.undoStep()
//    publish(new PlayerChanged)
  }
  def redo(): Unit = {
//    undoManager.redoStep()
//    publish(new PlayerChanged)
  }

  def saveTo(str:String): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/save"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              updateData(new PlayerChanged)
            } else {
              println("save failed")
            }
        }
    }
//    val fileIO = injector.instance[FileIOInterface](Names.named(str))
//    fileIO.save(players, field)
//    publish(new PlayerChanged)
  }
  def loadFrom(str:String): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = PlayerServer + "/load"
    ))
    responseFuture.onComplete {
      case Failure(_)
      => sys.error("Failed getting Json")
      case Success(value)
      =>
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Failed unmarshalling")
          case Success(value) =>
            val response = Json.parse(value)
            if ((response \ "success").get.toString.toBoolean) {
              updateData(new PlayerChanged)
            } else {
              println("load failed")
            }
        }
    }
//    val fileIO = injector.instance[FileIOInterface](Names.named(str))
//    field = fileIO.loadField
//    players = fileIO.loadPlayers
//    playerAmount = players.size
//    publish(new PlayerChanged)
  }
}
