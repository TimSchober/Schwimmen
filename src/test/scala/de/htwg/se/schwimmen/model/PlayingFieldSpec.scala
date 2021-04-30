package de.htwg.se.schwimmen.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingFieldSpec" should {
    val stack = CardStack()
    val field = PlayingField(stack)
    val player = Player("player1", stack)
    "swap one card when call a swapCard" in {
      val fieldCopy = field.cardsOnField
      val pl = field.swapCard(player.cardsOnHand, 2-1, 1 - 1)
      field.cardsOnField.last should be(player.cardsOnHand(1))
      pl.last should be(fieldCopy.head)
    }
    "swap all cards when call a swapAllCards" in {
      val fieldCopy = field.cardsOnField
      val pl = field.swapAllCards(player.cardsOnHand)
      pl should be(fieldCopy)
    }

  }

}
