package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.CardStack
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A schwimmen TUI" should {
    val players: List[String] = List("Tim")
    val controller = new Controller(null, Nil, null, 0)
    val tui = new TUI(controller)
//    "build the game til playable" in {
//      tui.gamestart()
//      controller.stack should be(new CardStack)
//      controller.field.cardsOnField.length should be(3)
//      controller.playerAmount should be(1)
//    }
  }
}
