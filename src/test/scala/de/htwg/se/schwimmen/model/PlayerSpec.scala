package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
    val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
    var player1 = Player("Tim")
    "have a name" in {
      player1.name should be("Tim")
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
    "be able to change all cards" in {
      player1 = player1.setCardsOnHand(playerli)
      player1.cardsOnHand should equal(playerli)
    }
    "give back a list of cards on the players hand after player has swapped" in {
      player1 = player1.setCardsOnHand(playerli)
      var field = PlayingField()
      field = field.setCardsOnField(fieldli)
      player1.swapCard(field, 0, 0) should equal(
        List(("7","hearts"),("jack","spade"),("queen","spade")))
      player1.swapCard(field, 1, 0) should equal(
        List(("10","spade"),("7","hearts"),("queen","spade")))
      player1.swapCard(field, 2, 0) should equal(
        List(("10","spade"),("jack","spade"),("7","hearts")))
    }
    "have a toString method" in {
      player1 = player1.setCardsOnHand(playerli)
      val builder = new StringBuilder
      builder.append("These are the cards in your hand:    ")
        .append("10").append(" of ").append("spade").append("s    ")
        .append("jack").append(" of ").append("spade").append("s    ")
        .append("queen").append(" of ").append("spade").append("s    ")
        .append("        ").append("Tim").append(" has ").append("2")
        .append(" lives left")
      player1.toString should equal(builder.toString())
    }
  }
}