package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val stack = CardStack()
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
  }
}