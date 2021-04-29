package de.htwg.se.schwimmen

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val player1 = Player("player1")
    "have a name" in {
      player1.name should be("player1")
    }
    "have a lifecount" in {
      player1.life should be(3)
    }
  }
}