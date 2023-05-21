package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api.*
import slick.sql.FixedSqlAction

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
object PlayerDAO {
  import PrivateExecutionContext._

  val playerTable = SlickPlayerTable.playersTable

  def insertPlayer(playerId: Long, name: String, life: Int, hsasKnocked: String,
                   fCardValue: String, fCardColor: String,
                   sCardValue: String, sCardColor: String,
                   tCardValue: String, tCardColor: String) = {
    val tableEntry = playerTable += (playerId, name, life, hsasKnocked,
                                      fCardValue, fCardColor,
                                      sCardValue, sCardColor,
                                      tCardValue, tCardColor)
    val futureId: Future[Int] = Connection.db.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(10000)
  }

  def getAllPlayers() = {
    val getAllCards = Connection.db.run(playerTable.result)
    getAllCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }

    Thread.sleep(10000)
  }

  def deletePlayer(): Unit = {
    Connection.db.run(SlickPlayerTable.playersTable.delete)
    Thread.sleep(5000)
  }

  def main(args: Array[String]): Unit = {
//    val player_id = 0L
//    val player_name = "A"
//    val player_life = 3
//    val has_knocked = "false"
//    val fcv = "7"
//    val fcc = "clubs"
//    val scv = "9"
//    val scc = "diamonds"
//    val tcv = "10"
//    val tcc = "spades"
//    insertPlayer(player_id, player_name, player_life, has_knocked, fcv, fcc, scv, scc, tcv, tcc)
//    deletePlayer()

  }
}
