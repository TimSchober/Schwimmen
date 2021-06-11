package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.{CardSelected, Controller, NewGame, PlayerAdded, PlayerAmountChanged, PlayerChanged, YesSelected}
import de.htwg.se.schwimmen.model.Player
import scala.util.{Failure, Success, Try}
import scala.swing.Reactor

class TUI(val controller: Controller) extends Reactor {

  listenTo(controller)
  controller.createNewGame()

  var playerCardInt = 0
  var input: String = ""
  def processInput(): Unit = {
    Try {input.toInt} match {
      case Success(e) =>
        if (controller.playerAmount == 0 && controller.field.processPlayerAmount(input.toInt)) {
          controller.setPlayerAmount(input.toInt)
        } else if ((input.toInt == 1 | input.toInt == 2 | input.toInt == 3) && playerCardInt == 0) {
          playerCardInt = input.toInt
          controller.cardSelected()
        } else if ((input.toInt == 1 | input.toInt == 2 | input.toInt == 3) && playerCardInt != 0) {
          controller.swapCards(playerCardInt - 1, input.toInt - 1)
          playerCardInt = 0
          controller.nextPlayer()
        }
      case Failure(e) =>
        input match {
          case "q" =>
          case "z" => controller.undo()
          case "r" => controller.redo()
          case _ =>
            if (controller.playerAmount > controller.players.size) {
              controller.addPlayer(input)
            } else {
              input match {
                case "y" =>
                  controller.yesSelected()
                case "all" =>
                  controller.swapAllCards()
                  controller.nextPlayer()
                case "k" =>
                  controller.setKnocked()
                  controller.nextPlayer()
                case "n" =>
                  controller.doNothing()
                  controller.nextPlayer()
                case _ =>
              }
            }
        }
    }
  }

  reactions += {
    case event: NewGame => printWelcome()
    case event: PlayerAmountChanged => println(typeYourNameString())
    case event: PlayerAdded => println(typeYourNameString())
    case event: YesSelected => println(firstInputString())
    case event: CardSelected => println(secondInputString())
    case event: PlayerChanged => println(nextPlayerString())
  }

  def printWelcome(): Unit = {
    println(sayWelcomeString())
  }

  def sayWelcomeString(): String = {
    """Welcome to Schwimmen!
      |How many players want to play?
      |Possible player amount is 2-9.
      |""".stripMargin
  }

  def statsString(pl: Player): String = {
    "\n" + controller.field.toString + "\n" + pl.toString() + "\n"
  }

  def typeYourNameString(): String = {
    if (controller.playerAmount <= controller.players.size) {
      statsString(controller.players.head) + firstOutputString()
    } else {
      s"\nPlayer ${controller.players.size + 1}, type your name:"
    }
  }

  def firstOutputString(): String = {
    if(controller.players.head.hasKnocked) {
      input = "q"
      return "end"//---> Darauf folgt ausszählen
    }
    s"\n${controller.players.head.name}, its your turn! " +
      s"Do you want to change a card?(y/n) or all cards?(all) or knock?(k)"
  }

  def nextPlayerString(): String = {
    statsString(controller.players.last) + statsString(controller.players.head) + firstOutputString()
  }
  def firstInputString(): String = {
    "which one of yours?(1/2/3)"
  }
  def secondInputString(): String = {
    "which one of the field?(1/2/3)"
  }
}
