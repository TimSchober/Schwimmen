package de.htwg.se.schwimmen.model.fieldComponent.fieldImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.schwimmen.model.fieldComponent._

case class Player @Inject() (name: String = "",
                  cardsOnHand: List[(String, String)] = Nil,
                  @Named("cardCount") cardCount: Double = 0.0,
                  @Named("hasKnocked") hasKnocked: Boolean = false,
                  @Named("life") life: Int = 3) extends PlayerInterface{

  def setName(nameString: String): Player = {
    copy(name = nameString)
  }

  def setCardsOnHand(threeCards: List[(String, String)]): Player = {
    copy(cardsOnHand = threeCards, cardCount = setCardCount(threeCards))
  }

  def setLife(l: Int): Player = {
    copy(life = l)
  }

  def setHasKnocked(h: Boolean): Player = {
    copy(hasKnocked = h)
  }

  def swapCard(field: PlayingFieldInterface, indexPlayer: Int, indexField: Int): List[(String, String)] =
    indexPlayer match {
      case 0 => List(field.cardsOnField(indexField), cardsOnHand(1), cardsOnHand.last)
      case 1 => List(cardsOnHand.head, field.cardsOnField(indexField), cardsOnHand.last)
      case 2 => List(cardsOnHand.head, cardsOnHand(1), field.cardsOnField(indexField))
    }

  override def toString: String = {
    val builder = new StringBuilder
    builder.append("These are the cards in your hand:    ")
    cardsOnHand.foreach((x: (String, String)) =>
      builder.append(x._1).append(" of ")
        .append(x._2).append("s    "))
    builder.append("        ").append(name).append(" has ").append(life).append(" lives left").toString()
  }

  def getColoursOfCards(threeCards: List[(String, String)]): List[String] = {
    List(threeCards.head._2, threeCards(1)._2, threeCards.last._2)
  }

  def getValuesOfCards(threeCards: List[(String, String)]): List[Int] = {
    List(getValue(threeCards.head._1), getValue(threeCards(1)._1), getValue(threeCards.last._1))
  }

  def getValue(value: String): Int = value match {
    case "7" => 7
    case "8" => 8
    case "9" => 9
    case "10" => 10
    case "jack" => 10
    case "queen" => 10
    case "king" => 10
    case "ace" => 11
  }

  def setCardCount(threeCards: List[(String, String)]): Double = {
    val colourList = getColoursOfCards(threeCards)
    val valueList = getValuesOfCards(threeCards)
    def threeEqualCards:Int = {
      if (colourList.head.equals(colourList.last) && colourList.head.equals(colourList(1))) {
        valueList.head + valueList(1) + valueList.last
      } else {
        0
      }
    }
    def leftEqualCards:Int = {
      if (colourList.head.equals(colourList(1))) {
        valueList.head + valueList(1)
      } else {
        0
      }
    }
    def rightEqualCards:Int = {
      if (colourList.last.equals(colourList(1))) {
        valueList.last + valueList(1)
      } else {
        0
      }
    }
    def outerEqualCards:Int = {
      if (colourList.head.equals(colourList.last)) {
        valueList.head + valueList.last
      } else {
        0
      }
    }
    if (threeCards.head._1.equals(threeCards(1)._1) && threeCards.head._1.equals(threeCards.last._1)) return 30.5
    List(threeEqualCards, leftEqualCards, rightEqualCards, outerEqualCards).max
  }
}
