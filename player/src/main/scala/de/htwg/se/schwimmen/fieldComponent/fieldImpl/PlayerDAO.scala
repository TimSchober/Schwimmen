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

  def getFirstPlayer(): Seq[(Long, String, Int, String,
    String, String,
    String, String,
    String, String)] = {

    val minId = playerTable.map(_.id).min
    val getCurrentPlayer = Connection.db.run(playerTable.filter(_.id >= minId).filter(_.id <= minId).result)
    getCurrentPlayer.onComplete {
      case Success(p) => println(s"Fetched get current player: $p")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, Int, String,
      String, String,
      String, String,
      String, String)] = Await.result(getCurrentPlayer, 5.seconds)

    result
  }

  def getCurrentPlayer(name: String): Seq[(Long, String, Int, String,
    String, String,
    String, String,
    String, String)] = {

    val getCurrentPlayer = Connection.db.run(playerTable.filter(_.name like name).result)
    getCurrentPlayer.onComplete {
      case Success(p) => println(s"Fetched get current player: $p")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, Int, String,
      String, String,
      String, String,
      String, String)] = Await.result(getCurrentPlayer, 5.seconds)

    result.toList
  }

  def getnextPlayer(name: String): Seq[(Long, String, Int, String,
    String, String,
    String, String,
    String, String)] = {

    val getCurrentPlayer = Connection.db.run(playerTable.filter(_.name like name).result)
    getCurrentPlayer.onComplete {
      case Success(p) => println(s"Fetched get current player: $p")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, Int, String,
      String, String,
      String, String,
      String, String)] = Await.result(getCurrentPlayer, 5.seconds)


    val getCurrentPlayer11 = Connection.db.run(playerTable.filter(_.id > result.toList.head._1).result)
    getCurrentPlayer11.onComplete {
      case Success(p) => println(s"Fetched get current player: $p")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result11: Seq[(Long, String, Int, String,
      String, String,
      String, String,
      String, String)] = Await.result(getCurrentPlayer11, 5.seconds)

    if(result11.toList.nonEmpty) {

      val getNextPlayer = Connection.db.run(playerTable.filter(_.id >= result.toList.head._1 + 1).filter(_.id <= result.toList.head._1 + 1).result)
      getNextPlayer.onComplete {
        case Success(p) => println(s"Fetched get current player: $p")
        case Failure(ex) => println(s"Fetching failed: $ex")
      }
      val result1: Seq[(Long, String, Int, String,
        String, String,
        String, String,
        String, String)] = Await.result(getNextPlayer, 5.seconds)

      result1.toList
    } else {
      getFirstPlayer()
    }

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
    //deletePlayer()
  }
}
