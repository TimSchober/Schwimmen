package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

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
object CardStackDAO {
  import PrivateExecutionContext._

  val cardTable = SlickTables.cardsTable

  def insertCard(cardId: Long, cardValue: String, cardColor: String) = {

    val tableEntry = cardTable += (cardId, cardValue, cardColor)
    val futureId: Future[Int] = Connection.db.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(500)
  }

  def getAllCards() = {
    val getAllCards = Connection.db.run(cardTable.result)
    getAllCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }

    Thread.sleep(10000)
  }

  def deletCard() = {
    Connection.db.run(SlickTables.cardsTable.delete)
    Thread.sleep(5000)
  }

//  def getCards(cardValue: String, cardColor: String): Unit = {
//
//    val getCard = Connection.db.run(cardTable.filter(_.cardValue.like(cardValue)).result)
//    getCard.onComplete {
//      case Success(cards) => println(s"Fetched: $cards")
//      case Failure(ex) => println(s"Fetching failed: $ex")
//    }
//
//    Thread.sleep(10000)
//  }
//
//  def updatCardTable(cardId: Long, cardValue: String, cardColor: String): Unit = {
//
//    val updateCard = cardTable.insertOrUpdate(cardId, cardValue, cardColor)
//    val futureId: Future[Int] = Connection.db.run(updateCard)
//
//    futureId.onComplete {
//      case Failure(exception) => println(s"Query faild, reason: $exception")
//      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
//    }
//    Thread.sleep(10000)
//  }

  def main(args: Array[String]) : Unit = {
//    val id = 0L
//    val cardValue = "9"
//    val cardColor = "diamond"
//    insertCard(id, cardValue, cardColor)
//    deletCard()

  }
}
