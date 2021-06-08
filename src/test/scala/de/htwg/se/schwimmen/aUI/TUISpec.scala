package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A TUI" should {
    val tui = new TUI(new Controller(null, Nil, null, 0))
    tui.controller.playerAmount = 2
    tui.controller.addPlayer("Tim")
    tui.controller.addPlayer("Ayaz")
    "return a welcome string" in {
      tui.sayWelcome() should equal("""Welcome to Schwimmen!
                                      |How many players want to play?
                                      |Possible player amount is 2-9.
                                      |""".stripMargin)
    }
    "print current stats of the player" in {
      tui.currentPlayerStats() should equal("\n" +
        tui.controller.field.toString + "\n" +
        tui.controller.players.head.toString() + "\n")
    }
    "test the input for a certain player amount" in {
      tui.input = "2"
      tui.processPlayerAmountInput() should equal("\nPlayer 1, type your name:")
      tui.input = "1"
      tui.processPlayerAmountInput() should equal("\nillegal input, try again")
    }
    "process the names of the players" in {
      tui.n = 1
      tui.controller.playerAmount = 2
      tui.processPlayerNameInput() should equal("\nPlayer 2, type your name:")
      tui.n = 2
      tui.processPlayerNameInput() should equal(tui.currentPlayerStats() + tui.firstOutput())
    }
    "give an output to which a player can answer" in {
      tui.firstOutput() should equal("\nTim, its your turn! " +
        "Do you want to change a card?(y/n) or all cards?(all) or knock?(k)")
      tui.controller.players = tui.controller.players.patch(0, Seq(tui.controller.players.head.setHasKnocked(true)), 1)
      tui.firstOutput() should equal("end")
    }
    "process the answer to the last question" in {
      tui.input = "y"
      tui.processFirstInput() should equal("which one of yours?(1/2/3)")
      tui.input = "all"
      tui.processFirstInput()
      tui.turn should equal(0)
      tui.input = "k"
      tui.processFirstInput()
      tui.turn should equal(0)
      tui.input = "n"
      tui.processFirstInput()
      tui.turn should equal(0)
      tui.input = "anything else"
      tui.processFirstInput() should equal("illegal input, try again")
    }
    "change players" in {
      tui.next()
      tui.controller.players.head.name should equal("Tim")
      tui.next()
      tui.controller.players.head.name should equal("Ayaz")
    }
    "process the answer to which one of yours?(1/2/3)" in {
      tui.input = "1"
      tui.processSecondInput() should equal("which one of the field?(1/2/3)")
    }
    "process the answer to which one of the field?(1/2/3)" in {
      tui.processSecondInput()
      tui.turn should be(2)
    }
    "match all the inputs" in {
      tui.input = "sayWelcome"
      tui.matchInput() should equal(tui.sayWelcome())
      tui.input = ""
      tui.matchInput() should equal("")

      var testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -2
      testtui.input = "1"
      var res = testtui.processPlayerAmountInput()
      testtui.turn = -2
      testtui.input = "1"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -1
      testtui.input = "Tim"
      res = testtui.processPlayerNameInput()
      testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -1
      testtui.input = "Tim"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = 0
      testtui.input = "anything"
      res = testtui.processFirstInput()
      testtui.turn = 0
      testtui.input = "anything"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = 1
      testtui.input = "1"
      res = testtui.processSecondInput()
      testtui.turn = 1
      testtui.input = "1"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.playerCardInt = 1
      testtui.turn = 2
      testtui.input = "1"
      testtui.matchInput()
      testtui.turn should be(0)
    }
    "update" in {
      tui.input = ""
      tui.update should be(true)
    }
    "return undo if input = z" in {
      tui.input = "z"
      tui.matchInput() should equal("undo")
    }
    "return redo if input = r" in {
      tui.input = "r"
      tui.matchInput() should equal("redo")
    }
    "print stats" in {
      tui.input = "printStats"
      tui.matchInput()
      tui.turn should be(-2)
    }
  }
}
