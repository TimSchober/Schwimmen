package de.htwg.se.schwimmen

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameCardSpec extends AnyWordSpec with Matchers {
  /*"A GameCard" when {
    "set to a wert and typ" should {
      val card = GameCard("7", "Heart")
      "return a wert and type of Card" in {
        card.wert should be("7")
        card.typ should be("Heart")
      }
      "return is a valid Card" in {
        card.isCard should be(true)
      }

    }
  }*/
  "A GameCard" should {
    "the number and the Colour of Card" in {
      val card = GameCard("7", "heart")
      card.number should be("7")
      card.colour should be("heart")
    }
    "retuen is a valid Card" in {
      val card = GameCard("8", "diamond")
      card.isCard should be(true)
    }

  }
}