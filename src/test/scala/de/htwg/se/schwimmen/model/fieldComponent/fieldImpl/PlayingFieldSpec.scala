package de.htwg.se.schwimmen.model.fieldComponent.fieldImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingField" should {
    var field = PlayingField()
    var player = Player("Tim")
    "set the given cards" in {
      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
      field = field.setCardsOnField(fieldli)
      field.cardsOnField should equal(fieldli)
    }
    "give back a list of cards on the field after player has swapped" in {
      val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
      player = player.setCardsOnHand(playerli)
      field = field.setCardsOnField(fieldli)
      field.swapCard(player, 0, 0) should equal(List(("10","spade"),("8","hearts"),("9","hearts")))
      field.swapCard(player, 0, 1) should equal(List(("7","hearts"),("10","spade"),("9","hearts")))
      field.swapCard(player, 0, 2) should equal(List(("7","hearts"),("8","hearts"),("10","spade")))
    }
    "have 2-9 player" in {
      field.processPlayerAmount(2) should be(true)
      field.processPlayerAmount(1) should be(false)
    }
    "have a toString method" in {
      field = field.setCardsOnField(List(("7","hearts"),("8","hearts"),("9","hearts")))
      val builder = new StringBuilder
      builder.append("These are the cards on the field:    ")
        .append("7").append(" of ").append("hearts").append("s    ")
        .append("8").append(" of ").append("hearts").append("s    ")
        .append("9").append(" of ").append("hearts").append("s    ")
      field.toString should equal(builder.toString())
    }
  }
}
