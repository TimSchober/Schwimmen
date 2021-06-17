package de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardStackSpec extends AnyWordSpec with Matchers {
  "A CardStack" should {
    val stack = CardStack()
    "have a specified number of cards" in {
      stack.rndCardStack.size should be(32)
    }
    "return three cards" in {
      stack.getThreeCards.size should be(3)
    }
    "delete those three cards" in {
      stack.delThreeCards.rndCardStack.size should be(29)
    }
  }
}
