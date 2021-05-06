package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A schwimmen TUI" should {
    val stack = new CardStack
    val field: PlayingField = PlayingField(stack)
    val players: List[Player] = List(Player("Tim", stack))
    val controller = new Controller(stack, players, field, 0)
    val tui = new TUI(controller)

  }
}
