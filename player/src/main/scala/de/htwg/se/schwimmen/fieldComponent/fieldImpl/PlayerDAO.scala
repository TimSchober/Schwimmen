package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api.*
import slick.sql.FixedSqlAction
import scala.concurrent.duration._

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
object PlayerDAO {
  import PrivateExecutionContext._

  val playerTable = SlickPlayerTable.playersTable
  val playingFieldTable = SlickPlayerTable.playingFieldTable

  def insertPlayer(playerId: Long, name: String, life: Int, hasKnocked: String,
                   fCardValue: String, fCardColor: String,
                   sCardValue: String, sCardColor: String,
                   tCardValue: String, tCardColor: String, turn: String): Int = {
    val tableEntry = playerTable += (playerId, name, life, hasKnocked,
                                      fCardValue, fCardColor,
                                      sCardValue, sCardColor,
                                      tCardValue, tCardColor, turn)
    val futureId: Future[Int] = Connection.db.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Await.result(futureId, 5.seconds)
  }

  def getAllPlayers: Seq[(Long, String, Int, String,
    String, String,
    String, String,
    String, String, String)] = {
    val getAllCards = Connection.db.run(playerTable.result)
    getAllCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, Int, String,
      String, String,
      String, String,
      String, String, String)] = Await.result(getAllCards, 5.seconds)
    result.toList
  }

  def deletePlayer(): Unit = {
    Await.result(Connection.db.run(playerTable.delete), 5.seconds)
  }

  def insertFieldCards(playingFieldId: Long,
                       fCardValue: String, fCardColor: String,
                       sCardValue: String, sCardColor: String,
                       tCardValue: String, tCardColor: String): Unit = {
    val tableEntry = playingFieldTable += (playingFieldId, fCardValue, fCardColor, sCardValue, sCardColor, tCardValue, tCardColor)
    val futureId: Future[Int] = Connection.db.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Await.result(futureId, 5.seconds)
  }

  def getFieldCards: Seq[(Long, String, String, String, String, String, String)] = {
    val getThreeCards = Connection.db.run(playingFieldTable.result)
    getThreeCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, String, String, String, String, String)] = Await.result(getThreeCards, 5.seconds)
    result.toList
  }

  def deletePlayingField(): Unit = {
    Await.result(Connection.db.run(playingFieldTable.delete), 5.seconds)
  }
}
