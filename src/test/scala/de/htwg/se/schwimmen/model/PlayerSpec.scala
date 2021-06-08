package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val stack = CardStack()
    val threeCards = stack.rndCardStack.take(3)
    var player1 = Player("player1", stack)
    player1 = player1.setCardsOnHand(player1.stack.getThreeCards)
    "have a name" in {
      player1.name should be("player1")
    }
    "have a lifecount" in {
      player1.life should be(3)
    }
    "set a lifecount" in {
      player1 = player1.setLife(2)
      player1.life should be(2)
    }
    "have a bool which says whether a player knocked or not" in {
      player1.hasKnocked should be(false)
    }
    "set this bool" in {
      player1 = player1.setHasKnocked(true)
      player1.hasKnocked should be(true)
    }
    "have three random cards" in {
      player1.cardsOnHand.length should be(3)
      player1.cardsOnHand should equal(threeCards)
    }
    "be able to change all cards" in {
      val li = List(("7","heart"), ("8","diamond"), ("9","spade"))
      player1 = player1.setCardsOnHand(li)
      player1.cardsOnHand should equal(li)
    }
    "have a toString method" in {
      player1 = player1.setCardsOnHand(threeCards)
      val builder = new StringBuilder
      builder.append("These are the cards in your hand:    ")
        .append(threeCards.head._1).append(" of ").append(threeCards.head._2).append("s    ")
        .append(threeCards(1)._1).append(" of ").append(threeCards(1)._2).append("s    ")
        .append(threeCards.last._1).append(" of ").append(threeCards.last._2).append("s    ")
        .append("        ").append(player1.name).append(" has ").append(player1.life)
        .append(" lives left")
      player1.toString should equal(builder.toString())
    }
  }
}