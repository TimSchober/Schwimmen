package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A TUI" should {
    val tui = new TUI(new Controller(null,Nil,null,0))
    tui.controller.createCardStack()
    tui.controller.createField()
    "return a welcome string" in {
      tui.sayWelcome() should equal("""Welcome to Schwimmen!
                                      |How many players want to play?
                                      |Possible player amount is 2-9.
                                      |""".stripMargin)
    }
    "start up a game" in {
      tui.gamestart(1, List("Tim")) should be(-1)
      tui.gamestart(2, List("Tim", "Ayaz")) should be(0)
      tui.controller.stack should be(new CardStack)
      tui.controller.playerAmount should be(2)
      tui.controller.field should be(PlayingField(tui.controller.stack))
      tui.controller.players.length should be(2)
    }
    "process the players Input" in {
      val p = Player("Tim", new CardStack)
      tui.processInput(p, "y") should equal("which one of yours?(1/2/3)")
      tui.processInput(p, "all") should equal("nextTurn")
      tui.processInput(p, "k") should equal("nextTurn")
      tui.processInput(p, "n") should equal("nextTurn")
      tui.processInput(p, "lol") should equal("illegal input, try again")
    }
    "process the next input when the first was y" in {
      val p = Player("Tim", new CardStack)
      val pcopy = p.cardsOnHand
      tui.processInput2(p, 1, 1)
      pcopy.head should be(tui.controller.field.cardsOnField.head)
    }
  }
}
