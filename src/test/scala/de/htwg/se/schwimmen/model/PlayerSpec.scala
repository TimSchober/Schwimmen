package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val stack = CardStack()
    val threeCards = stack.rndCardStack.take(3)
    val player1 = Player("player1", stack)
    "have a name" in {
      player1.name should be("player1")
    }
    "have a lifecount" in {
      player1.life should be(3)
    }
    "have three random cards" in {
      player1.cardsOnHand.length should be(3)
    }
    "have a bool which says whether a player knocked or not" in {
      player1.hasKnocked should be(false)
    }
    "have a toString method" in {
      val builder = new StringBuilder
      builder.append("These are the cards in your Hand:    ")
        .append(threeCards.head._1).append(" of ").append(threeCards.head._2).append("s    ")
        .append(threeCards(1)._1).append(" of ").append(threeCards(1)._2).append("s    ")
        .append(threeCards.last._1).append(" of ").append(threeCards.last._2).append("s    ")
        .append("        ").append(player1.name).append(" has ").append(player1.life)
        .append(" lives left")
      player1.toString should equal(builder.toString())
    }
  }
}