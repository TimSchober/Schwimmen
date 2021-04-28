package de.htwg.se.schwimmen

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" when {
    "the Player's name and the cards what he should have" in {
      val player1 = Player("player1")
      player1.name should be("player1")
    }
  }
}