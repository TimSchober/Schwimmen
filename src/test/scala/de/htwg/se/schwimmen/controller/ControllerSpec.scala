package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.CardStack
import de.htwg.se.schwimmen.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers{

  "A controller observed by an Observer" should {
    val players: List[String] = List("Tim")
    val controller = new Controller(null, Nil, null, 0)
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Boolean = updated
    }
    controller.add(observer)
    "notify its observer after creating a stack of cards" in {
      controller.createCardStack()
      observer.updated should be(true)
      controller.stack should be(new CardStack)
    }
    "notify its observer after creating all players" in {
      controller.playerAmount = players.length
      controller.createPlayers(players)
      observer.updated should be(true)
      controller.players.head.name should be("Tim")
    }
    "notify its observer after creating a field" in {
      controller.createField()
      observer.updated should be(true)
      controller.field.cardsOnField.length should be(3)
    }
    "print all players" in {
      controller.printPlayer()
    }
  }
}
