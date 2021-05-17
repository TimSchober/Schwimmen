package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.CardStack
import de.htwg.se.schwimmen.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers{

  "A controller observed by an Observer" should {
    val players: List[String] = List("Tim")
    val controller = new Controller(null, Nil, null, null, 0)
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Boolean = {
        updated = true
        updated
      }
    }
    controller.add(observer)
    "create a stack of cards" in {
      controller.createCardStack()
      //observer.updated should be(true)
      controller.stack should be(new CardStack)
    }
    "create all players" in {
      controller.playerAmount = players.length
      controller.createPlayers(players)
      //observer.updated should be(true)
      controller.players.head.name should be("Tim")
    }
    "create a field" in {
      controller.createField()
      //observer.updated should be(true)
      controller.field.cardsOnField.length should be(3)
    }
    "add a single player" in {
      controller.playerAmount = 2
      controller.addPlayer("Ayaz")
      controller.players.last.name should equal("Ayaz")
      controller.currentPlayer.name should equal("Tim")
    }
    "get the next player" in {
      controller.nextPlayer()
      controller.currentPlayer.name should equal("Ayaz")
    }
  }
}
