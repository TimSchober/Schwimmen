package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

import slick.jdbc.PostgresProfile.api._

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
object CardStackDAO {
  import PrivateExecutionContext._

  val tableEntry = SlickTables.cardsTable += (1L, "7", "clubs")
  val futureId: Future[Int] = Connection.db.run(tableEntry)
  def insertCard() = {

    futureId.onComplete {
      case Failure(exception) => println(s"Query faild, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(10000)
  }

  def getallCards() = {
    val resultFuture = Connection.db.run(SlickTables.cardsTable.result)
    resultFuture.onComplete {
      case Success(cards) => println(s"Feched: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }

    Thread.sleep(10000)
  }

  def getCards(cardValue: String, cardColor: String) = {
    val resultFuture = Connection.db.run(SlickTables.cardsTable.filter(_.cardValue.like(cardValue)).result)
    resultFuture.onComplete {
      case Success(cards) => println(s"Feched: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }

    Thread.sleep(10000)
  }

  // Update funktioniert nicht
  def updatCardTable() = {
    //val catdToUpdate = SlickTables.cardsTable.filter(_.id === 1L)
    //val updateCard = catdToUpdate.update(2, "8", "diamonds")

    val updateCard = SlickTables.cardsTable.filter(_.cardValue === "7").update(3L, "9", "heart")
    val futureId: Future[Int] = Connection.db.run(updateCard)

    futureId.onComplete {
      case Failure(exception) => println(s"Query faild, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(10000)
  }

  def deletCard(cardValue: String) = {
    Connection.db.run(SlickTables.cardsTable.filter(_.cardValue.like(cardValue)).delete)
    Thread.sleep(5000)
  }
  def main(args: Array[String]) : Unit = {
    //insertCard()
    //getallCards()
    //val cardValue = "7"
    //val cardColor = "clubs"
    //getCards(cardValue, cardColor)
    //updatCardTable()
    deletCard("7")
  }
}
