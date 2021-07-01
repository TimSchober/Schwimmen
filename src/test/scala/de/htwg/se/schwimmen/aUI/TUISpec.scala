package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.controllerComponent.NewGame
import de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl.Controller
import de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl.CardStack
import de.htwg.se.schwimmen.model.fieldComponent._
import de.htwg.se.schwimmen.model.fieldComponent.fieldImpl.{Player, PlayingField}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec with Matchers{

  "A TUI" should {
    var stack = CardStack()
    stack = stack.setCardStack()
    val pl1 = Player("Tim", cardsOnHand = List(("7", "heart"), ("8", "heart"), ("9", "heart")))
    val pl2 = Player("Ayaz", cardsOnHand = List(("7", "diamond"), ("8", "diamond"), ("9", "diamond")))
    val field = PlayingField(cardsOnField = List(("7", "spade"), ("8", "spade"), ("9", "spade")))
    val controller = new Controller(stack, List(pl1, pl2), field, 2)
    val tui = new TUI(controller)
    tui.controller.stack = stack
    tui.controller.field = field
    tui.controller.publish(new NewGame)
    "say welcome" in {
      tui.sayWelcomeString() should equal("""Welcome to Schwimmen!
                                            |How many players want to play?
                                            |Possible player amount is 2-9.
                                            |""".stripMargin)
    }
    "print stats" in {
      tui.statsString(pl1) should equal("\n" +
        "These are the cards on the field:    7 of spades    8 of spades    9 of spades    " + "\n" +
        "These are the cards in your hand:    7 of hearts    8 of hearts    9 of hearts" +
        "            Tim has 3 lives left" + "\n")
    }
    "print type your name" in {
      tui.typeYourNameString() should equal(tui.statsString(pl1) + tui.firstOutputString())
      tui.controller.playerAmount = 3
      tui.typeYourNameString() should equal("\nPlayer 3, type your name:")
      tui.controller.playerAmount = 2
    }
    "print stats of the next player" in {
      tui.nextPlayerString() should equal(tui.statsString(pl2) + tui.statsString(pl1) + tui.firstOutputString())
    }
    "say the player that its their turn" in {
      tui.firstOutputString() should equal("\nTim, its your turn! " +
        "Do you want to change a card?(y/n) or all cards?(all) or knock?(k)")
      tui.controller.setKnocked()
      val fostr = tui.firstOutputString()
      var plList: List[PlayerInterface] = List()
      for (pl <- tui.controller.players) {
        plList = plList :+ pl.setLife(3)
      }
      tui.controller.players = plList.reverse
      fostr should equal(tui.endOfGameStats())
    }
    "process the input made by the player" in {
      val testtui = new TUI(new Controller(stack, Nil, field, 0))
      testtui.input = "2"
      testtui.processInput() should equal("player amount set")
      testtui.controller.addPlayer("Tim")
      testtui.input = "2"
      testtui.processInput() should equal("card selected")
      testtui.processInput() should equal("cards swapped and player changed")
      testtui.input = "10"
      testtui.processInput() should equal("illegal input")
      testtui.input = "z"
      testtui.processInput() should equal("undo")
      testtui.input = "r"
      testtui.processInput() should equal("redo")
      testtui.controller.playerAmount = 2
      testtui.input = "Ayaz"
      testtui.processInput() should equal("player added")
      testtui.input = "y"
      testtui.processInput() should equal("yes selected")
      testtui.input = "all"
      testtui.processInput() should equal("all cards swapped and player changed")
      testtui.input = "k"
      testtui.processInput() should equal("player knock set and player changed")
      testtui.input = "n"
      testtui.processInput() should equal("player changed")
      testtui.input = "anything else"
      testtui.processInput() should equal("illegal input")
      testtui.input = "nr"
      testtui.processInput() should equal("next round")
      testtui.input = "saveXml"
      testtui.processInput() should equal("saved to Xml")
      testtui.input = "saveJson"
      testtui.processInput() should equal("saved to Json")
      testtui.input = "loadXml"
      testtui.processInput() should equal("loaded from Xml")
      testtui.input = "loadJson"
      testtui.processInput() should equal("loaded from Json")
    }
    "get ready for next round" in {
      var pl1 = Player("Tim")
      var pl2 = Player("Ayaz")
      pl1 = pl1.setCardsOnHand(List(("7", "heart"), ("8", "heart"), ("9", "heart")))
      pl2 = pl2.setCardsOnHand(List(("7", "diamond"), ("8", "diamond"), ("10", "diamond")))
      var tui = new TUI(new Controller(CardStack(), List(pl1, pl2), PlayingField(), 2))
      tui.endOfGameStats() should equal("\nAyaz:    25.0 points    3 lives left" + "\nTim:    24.0 points    3 lives left"
      + "\nTim, lost a Life\nstart next round with(nr)")
//      pl1 = pl1.setLife(0)
//      tui = new TUI(new Controller(CardStack(), List(pl1, pl2), PlayingField(), 2))
//      tui.endOfGameStats() should equal("Tim you're out" + "\nAyaz:    25.0 points    3 lives left" + "\nTim:    24.0 points    0 lives left"
//        + "\nTim, lost a Life\nAyaz, Congratulations you've won the game:)")
    }
  }
}
