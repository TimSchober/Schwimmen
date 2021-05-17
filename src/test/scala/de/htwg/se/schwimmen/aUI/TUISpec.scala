package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A TUI" should {
    val tui = new TUI(new Controller(null, Nil, null, null, 0))
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
        tui.controller.currentPlayer.toString() + "\n")
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
      tui.controller.players.head.hasKnocked = true
      tui.firstOutput() should equal("end")
    }
    "process the answer to the last question" in {
      var ret = tui.currentPlayerStats()
      tui.controller.nextPlayer()
      ret = ret + tui.currentPlayerStats() + tui.firstOutput()
      tui.input = "y"
      tui.processFirstInput() should equal("which one of yours?(1/2/3)")
      tui.input = "all"
      tui.controller.currentPlayer = tui.controller.players.head
      tui.controller.currentPlayer.cardsOnHand =
        tui.controller.field.swapAllCards(tui.controller.currentPlayer.cardsOnHand)
      tui.processFirstInput() should equal(ret)
      tui.input = "k"
      tui.controller.currentPlayer = tui.controller.players.head
      tui.processFirstInput() should equal(ret)
      tui.input = "n"
      tui.controller.currentPlayer = tui.controller.players.head
      tui.processFirstInput() should equal(ret)
      tui.input = "anything else"
      tui.processFirstInput() should equal("illegal input, try again")
    }
    "change players" in {
      tui.controller.currentPlayer = tui.controller.players.head
      var ret = tui.currentPlayerStats()
      tui.controller.nextPlayer()
      ret = ret + tui.currentPlayerStats() + tui.firstOutput()
      tui.controller.currentPlayer = tui.controller.players.head
      tui.next should equal(ret)
      tui.controller.currentPlayer.name should equal("Ayaz")
      tui.next()
      tui.controller.currentPlayer.name should equal("Tim")
    }
    "process the answer to which one of yours?(1/2/3)" in {
      tui.input = "1"
      tui.processSecondInput() should equal("which one of the field?(1/2/3)")
    }
    "process the answer to which one of the field?(1/2/3)" in {
      tui.controller.currentPlayer = tui.controller.players.head
      tui.controller.currentPlayer.cardsOnHand = tui.controller.field.swapCard(
        tui.controller.currentPlayer.cardsOnHand, 0, 0)
      val ret = tui.next()
      tui.controller.currentPlayer = tui.controller.players.head
      tui.controller.currentPlayer.cardsOnHand = tui.controller.field.swapCard(
        tui.controller.currentPlayer.cardsOnHand, 0, 0)
      tui.processThirdInput() should equal(ret)
    }
    "match all the inputs" in {
      tui.input = "sayWelcome"
      tui.matchInput() should equal(tui.sayWelcome())
      tui.input = ""
      tui.matchInput() should equal("")

      var testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -2
      testtui.input = "1"
      var res = testtui.processPlayerAmountInput()
      testtui.turn = -2
      testtui.input = "1"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -1
      testtui.input = "Tim"
      res = testtui.processPlayerNameInput()
      testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = -1
      testtui.input = "Tim"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = 0
      testtui.input = "anything"
      res = testtui.processFirstInput()
      testtui.turn = 0
      testtui.input = "anything"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.turn = 1
      testtui.input = "1"
      res = testtui.processSecondInput()
      testtui.turn = 1
      testtui.input = "1"
      testtui.matchInput() should equal(res)

      testtui = new TUI(new Controller(null, Nil, null, null, 0))
      testtui.controller.playerAmount = 2
      testtui.controller.addPlayer("Tim")
      testtui.controller.addPlayer("Ayaz")
      testtui.playerCardInt = 1
      testtui.turn = 2
      testtui.input = "1"
      res = testtui.processThirdInput()
      testtui.controller.currentPlayer = testtui.controller.players.head
      testtui.controller.currentPlayer.cardsOnHand = testtui.controller.field.swapCard(
        testtui.controller.currentPlayer.cardsOnHand, 0, 0)
      testtui.turn = 2
      testtui.input = "1"
      testtui.matchInput() should equal(res)
    }
    "update" in {
      tui.input = ""
      tui.update should be(true)
    }
  }
}
