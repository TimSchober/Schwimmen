package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardStackSpec extends AnyWordSpec with Matchers {
  "A CardStack" should {
    val stack = CardStack()
    "have a specified number of card" in {
      stack.getRndCardStack.size should be(32)
    }
    "return specified number of cards" in {
      stack.getThreeCards.size should be(3)
    }
  }
}
