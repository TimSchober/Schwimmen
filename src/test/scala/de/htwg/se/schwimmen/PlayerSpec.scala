package de.htwg.se.schwimmen

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" when {
    "set to a name and status" should {
      val player1 = Player("Player1", true)
      "return the status, He Won" in {
        player1.hasWon should be(true)
      }
      "have a name Player1" in {
        player1.name should be("Player1")
        player1.toString should be("Player1")
      }
    }
  }
}
