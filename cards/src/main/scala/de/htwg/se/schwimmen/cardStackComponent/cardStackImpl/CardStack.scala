package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

import com.google.inject.Inject
import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.cardStackComponent.*
import play.api.libs.json.{JsObject, Json}

import scala.util.Random

case class CardStack @Inject() (rndCardStack: List[(String, String)] = Nil) extends CardStackInterface{

  def setCardStack(): CardStack = {
    val num = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
    val col = List("heart", "diamond", "spade", "club")
    val fullCardStack: List[(String, String)] = num.flatMap(n => col.map(x => (n, x)))
    val randomCardStack = copy(rndCardStack = Random.shuffle(fullCardStack))
//    val id = 0
//    CardStackDAO.deletCard()
//    for (x <- randomCardStack.rndCardStack) {
//      CardStackDAO.insertCard(id + 1, x._1, x._2)
//    }
    randomCardStack
  }

  def getThreeCards: List[(String, String)] = {

    rndCardStack.take(3)
  }

  def delThreeCards: CardStack = {
    copy(rndCardStack.drop(3))
  }

  def getThreeCardsInJsonFormat: JsObject = {
    val threeCardsList = rndCardStack.take(3)
    val JsonObject = Json.obj(
      "firstCard" -> Json.obj(
        "Value" -> threeCardsList.head._1,
        "Color" -> threeCardsList.head._2,
      ),
      "secondCard" -> Json.obj(
        "Value" -> threeCardsList(1)._1,
        "Color" -> threeCardsList(1)._2,
      ),
      "thirdCard" -> Json.obj(
        "Value" -> threeCardsList.last._1,
        "Color" -> threeCardsList.last._2,
      )
    )
    JsonObject
  }
}
