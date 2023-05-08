package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import com.google.inject.Inject
import de.htwg.se.schwimmen.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.fieldComponent.*
import play.api.libs.json.{JsObject, Json}

case class PlayingField @Inject() (cardsOnField: List[(String, String)] = Nil) extends PlayingFieldInterface{

  override def toString: String = {
    val builder = new StringBuilder
    builder.append("These are the cards on the field:    ")
    cardsOnField.foreach((x:(String, String)) =>
      builder.append(x._1).append(" of ")
        .append(x._2).append("s    "))
    builder.toString()
  }

  def setCardsOnField(threeCards: List[(String, String)]): PlayingField = {
    copy(cardsOnField = threeCards)
  }

  def swapCard(player: PlayerInterface, indexPlayer: Int, indexField: Int): List[(String, String)] = {
    indexField match {
      case 0 => List(player.cardsOnHand(indexPlayer), cardsOnField(1), cardsOnField.last)
      case 1 => List(cardsOnField.head, player.cardsOnHand(indexPlayer), cardsOnField.last)
      case 2 => List(cardsOnField.head, cardsOnField(1), player.cardsOnHand(indexPlayer))
    }
  }

  def processPlayerAmount(amount: Int): Boolean = {
    amount match {
      case 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 => true
      case _ => false
    }
  }

  def fieldDataToJsonObject: JsObject = {
    Json.obj(
      "cardsOnField" -> Json.obj(
        "firstCard" -> Json.obj(
          "Value" -> cardsOnField.head._1,
          "Color" -> cardsOnField.head._2
        ),
        "secondCard" -> Json.obj(
          "Value" -> cardsOnField(1)._1,
          "Color" -> cardsOnField(1)._2
        ),
        "thirdCard" -> Json.obj(
          "Value" -> cardsOnField.last._1,
          "Color" -> cardsOnField.last._2
        )
      )
    )
  }
}
