package de.htwg.se.schwimmen

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameCardSpec extends AnyWordSpec with Matchers {
  "A GameCard" when {
    "set to a number and colour" should {
      val card = GameCard("7", "heart")
      "return a number and colour of Card" in {
        card.number should be("7")
        card.colour should be("heart")
      }
      "return is a valid Card" in {
        card.isCard should be(true)
      }

    }
  }

}