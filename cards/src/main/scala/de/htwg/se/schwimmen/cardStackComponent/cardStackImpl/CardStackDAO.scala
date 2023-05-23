package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api.*
import slick.sql.FixedSqlAction
import scala.concurrent.Await
import scala.concurrent.duration._

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
object CardStackDAO {
  import PrivateExecutionContext._

  val cardTable = SlickCardTable.cardsTable

  def insertCard(cardId: Long, cardValue: String, cardColor: String) = {

    val tableEntry = cardTable += (cardId, cardValue, cardColor)
    val futureId: Future[Int] = Connection.db.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(500)
  }

  def getThreeCards(): Seq[(Long, String, String)] = {
    val minId = cardTable.map(_.id).min
    val maxId = minId + 2L
    val getAllCards = Connection.db.run(cardTable.filter(_.id >= minId).filter(_.id <= maxId).result)
    getAllCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, String)] = Await.result(getAllCards, 5.seconds)

    Connection.db.run(cardTable.filter(_.id >= minId).filter(_.id <= maxId).delete)

    result.toList
  }

  def deletCard() = {
    Connection.db.run(SlickCardTable.cardsTable.delete)
    Thread.sleep(5000)
  }

//  def main(args: Array[String]) : Unit = {
//    println(getThreeCards())
//
//  }
}
