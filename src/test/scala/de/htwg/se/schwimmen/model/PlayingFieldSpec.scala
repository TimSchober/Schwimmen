package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingField" should {
    val stack = CardStack()
    val field = PlayingField(stack)
    val player = Player("player1", stack)
    "swap one card when call swapCard" in {
      val fieldCardsList = field.cardsOnField
      var plCards = field.swapCard(player.cardsOnHand, 0, 0)
      plCards.head should be(fieldCardsList.head)
      plCards = field.swapCard(player.cardsOnHand, 1, 1)
      plCards(1) should be(fieldCardsList(1))
      plCards = field.swapCard(player.cardsOnHand, 2, 2)
      plCards.last should be(fieldCardsList.last)
    }
    "swap all cards when call swapAllCards" in {
      val fieldCopy = field.cardsOnField
      val playerCards = field.swapAllCards(player.cardsOnHand)
      playerCards should be(fieldCopy)
    }
    "have three cards in cardsOnField" in {
      field.cardsOnField.length should be(3)
    }
    "have 2-9 player" in {
      field.processPlayerAmount(2) should be(true)
      field.processPlayerAmount(1) should be(false)
    }
    "have a toString method" in {
      val threeCards = stack.rndCardStack.take(3)
      val field = PlayingField(stack)
      val builder = new StringBuilder
      builder.append("These are the cards on the field:    ")
        .append(threeCards.head._1).append(" of ").append(threeCards.head._2).append("s    ")
        .append(threeCards(1)._1).append(" of ").append(threeCards(1)._2).append("s    ")
        .append(threeCards.last._1).append(" of ").append(threeCards.last._2).append("s    ")
      field.toString should equal(builder.toString())
    }
  }
}
