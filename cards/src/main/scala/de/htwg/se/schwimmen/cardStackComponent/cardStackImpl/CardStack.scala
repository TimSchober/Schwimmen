package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

import com.google.inject.Inject
import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.cardStackComponent.*
import play.api.libs.json.Json

import scala.util.Random

case class CardStack @Inject() (rndCardStack: List[(String, String)] = Nil) extends CardStackInterface{

  def setCardStack(): CardStack = {
    val num = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
    val col = List("heart", "diamond", "spade", "club")
    val fullCardStack: List[(String, String)] = num.flatMap(n => col.map(x => (n, x)))
    copy(rndCardStack = Random.shuffle(fullCardStack))
  }

  def getThreeCards: List[(String, String)] = {
    rndCardStack.take(3)
  }

  def delThreeCards: CardStack = {
    copy(rndCardStack.drop(3))
  }

  def getThreeCardsInJsonFormat: String = {
    val threeCardsList = rndCardStack.take(3)
    val Jsonobject = Json.obj(
      "firstCard" -> Json.obj(
        "Value" -> threeCardsList.head._1.toString,
        "Color" -> threeCardsList.head._2.toString,
      ),
      "secondCard" -> Json.obj(
        "Value" -> threeCardsList(1)._1.toString,
        "Color" -> threeCardsList(1)._2.toString,
      ),
      "thirdCard" -> Json.obj(
        "Value" -> threeCardsList.last._1.toString,
        "Color" -> threeCardsList.last._2.toString,
      )
    )
    Jsonobject.toString
  }
}
