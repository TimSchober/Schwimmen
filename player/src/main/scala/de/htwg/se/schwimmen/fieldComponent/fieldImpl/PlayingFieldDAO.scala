package de.htwg.se.schwimmen.fieldComponent.fieldImpl


import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api.*
import slick.sql.FixedSqlAction
import scala.concurrent.duration._

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object PrivateExecutionContext2 {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
object PlayingFieldDAO {
  import PrivateExecutionContext2._

  val playingFieldTable = SlickPlayingTable.playingFieldTable

  def insertFieldCards(playingFieldId: Long,
                       fCardValue: String, fCardColor: String,
                       sCardValue: String, sCardColor: String,
                       tCardValue: String, tCardColor: String): Unit = {
    val tableEntry = playingFieldTable += (playingFieldId, fCardValue, fCardColor, sCardValue, sCardColor, tCardValue, tCardColor)
    val futureId: Future[Int] = Connection2.db2.run(tableEntry)
    futureId.onComplete {
      case Failure(exception) => println(s"InsertQuery failed, reason: $exception")
      case Success(newCardId) => println(s"Query was successful, new is id $newCardId")
    }
    Thread.sleep(10000)
  }

  def getFieldCards(): Seq[(Long, String, String, String, String, String, String)] = {
    val minId = playingFieldTable.map(_.id).min
    val maxId = minId + 2L
    val getThreeCards = Connection2.db2.run(playingFieldTable.filter(_.id >= minId).filter(_.id <= maxId).result)
    getThreeCards.onComplete {
      case Success(cards) => println(s"Fetched get all cards: $cards")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    val result: Seq[(Long, String, String, String, String, String, String)] = Await.result(getThreeCards, 5.seconds)

    Connection2.db2.run(playingFieldTable.filter(_.id >= minId).filter(_.id <= maxId).delete)

    result.toList
  }

  def deletePlayingField(): Unit = {
    Connection2.db2.run(playingFieldTable.delete)
    Thread.sleep(5000)
  }

  def main(args: Array[String]): Unit = {
    insertFieldCards(1L, "7", "clubs", "8", "clubs", "9", "clubs")
    //deletePlayingField()
  }


}
