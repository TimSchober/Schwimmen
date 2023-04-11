package de.htwg.se.schwimmen.fieldImpl

import de.htwg.se.schwimmen.fieldComponent.fieldImpl.{Player, PlayingField}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val playerList = List(("10","spade"),("jack","spade"),("queen","spade"))
    val fieldList = List(("7","hearts"),("8","hearts"),("9","hearts"))
    val player1 = Player("Tim")
    "have a name" in {
      player1.name should be("Tim")
    }
    "have a lifecount" in {
      player1.life should be(3)
    }
    "set a lifecount" in {
      val playerOne = player1.setLife(2)
      playerOne.life should be(2)
    }
    "have a bool which says whether a player knocked or not" in {
      player1.hasKnocked should be(false)
    }
    "set this bool" in {
      val playerOne = player1.setHasKnocked(true)
      playerOne.hasKnocked should be(true)
    }
    "be able to change all cards" in {
      val playerOne = player1.setCardsOnHand(playerList)
      playerOne.cardsOnHand should equal(playerList)
    }
    "give back a list of cards on the players hand after player has swapped" in {
      val playerOne = player1.setCardsOnHand(playerList)
      val init_field = PlayingField()
      val field = init_field.setCardsOnField(fieldList)
      playerOne.swapCard(field, 0, 0) should equal(
        List(("7","hearts"),("jack","spade"),("queen","spade")))
      playerOne.swapCard(field, 1, 0) should equal(
        List(("10","spade"),("7","hearts"),("queen","spade")))
      playerOne.swapCard(field, 2, 0) should equal(
        List(("10","spade"),("jack","spade"),("7","hearts")))
    }
    "have a toString method" in {
      val playerOne = player1.setCardsOnHand(playerList)
      val builder = new StringBuilder
      builder.append("These are the cards in your hand:    ")
        .append("10").append(" of ").append("spade").append("s    ")
        .append("jack").append(" of ").append("spade").append("s    ")
        .append("queen").append(" of ").append("spade").append("s    ")
        .append("        ").append("Tim").append(" has ").append("3")
        .append(" lives left")
      playerOne.toString should equal(builder.toString())
    }
    "get the value of a cards face" in {
      player1.getValue("7") should be(7)
      player1.getValue("8") should be(8)
      player1.getValue("9") should be(9)
      player1.getValue("10") should be(10)
      player1.getValue("jack") should be(10)
      player1.getValue("queen") should be(10)
      player1.getValue("king") should be(10)
      player1.getValue("ace") should be(11)

    }
  }
}