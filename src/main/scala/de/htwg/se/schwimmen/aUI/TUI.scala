package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.controllerComponent._
import de.htwg.se.schwimmen.model.EasyStrategy
import de.htwg.se.schwimmen.model.fieldComponent._

import scala.util.{Failure, Success, Try}
import scala.swing.Reactor

class TUI(val controller: ControllerInterface) extends Reactor {

  listenTo(controller)
  controller.createNewGame()
  val strat = new EasyStrategy
  var playerCardInt = 0
  var input: String = ""
  def processInput(): String = {
    Try {input.toInt} match {
      case Success(e) =>
        if (controller.playerAmount == 0 && controller.field.processPlayerAmount(input.toInt)) {
          controller.setPlayerAmount(input.toInt)
          return "player amount set"
        } else if ((input.toInt == 1 | input.toInt == 2 | input.toInt == 3) && playerCardInt == 0) {
          playerCardInt = input.toInt
          controller.cardSelected()
          return "card selected"
        } else if ((input.toInt == 1 | input.toInt == 2 | input.toInt == 3) && playerCardInt != 0) {
          controller.swapCards(playerCardInt - 1, input.toInt - 1)
          playerCardInt = 0
          controller.nextPlayer()
          return "cards swapped and player changed"
        }
        "illegal input"
      case Failure(e) =>
        input match {
          case "nr" =>
            controller.nextRound()
            "next round"
          case "q" => "input set to q"
          case "z" =>
            controller.undo()
            "undo"
          case "r" =>
            controller.redo()
            "redo"
          case _ =>
            if (controller.playerAmount > controller.players.size) {
              controller.addPlayer(input)
              "player added"
            } else {
              input match {
                case "y" =>
                  controller.yesSelected()
                  "yes selected"
                case "all" =>
                  controller.swapAllCards()
                  controller.nextPlayer()
                  "all cards swapped and player changed"
                case "k" =>
                  controller.setKnocked()
                  controller.nextPlayer()
                  "player knock set and player changed"
                case "n" =>
                  controller.doNothing()
                  controller.nextPlayer()
                  "player changed"
                case _ =>
                  "illegal input"
              }
            }
        }
    }
  }

  reactions += {
    case event: NewGame => println(sayWelcomeString())
    case event: PlayerAmountChanged => println(typeYourNameString())
    case event: PlayerAdded => println(typeYourNameString())
    case event: YesSelected => println("which one of yours?(1/2/3)")
    case event: CardSelected => println("which one of the field?(1/2/3)")
    case event: PlayerChanged => println(nextPlayerString())
  }

  def sayWelcomeString(): String = {
    """Welcome to Schwimmen!
      |How many players want to play?
      |Possible player amount is 2-9.
      |""".stripMargin
  }

  def statsString(pl: PlayerInterface): String = {
    "\n" + controller.field.toString + "\n" + pl.toString + "\n"
  }

  def typeYourNameString(): String = {
    if (controller.playerAmount <= controller.players.size) {
      statsString(controller.players.head) + firstOutputString()
    } else {
      s"\nPlayer ${controller.players.size + 1}, type your name:"
    }
  }

  def firstOutputString(): String = {
    if (controller.players.head.hasKnocked || strat.checkStop(controller.players.last)) return endOfGameStats()
    s"\n${controller.players.head.name}, its your turn! " +
      s"Do you want to change a card?(y/n) or all cards?(all) or knock?(k)"
  }

  def nextPlayerString(): String = {
    statsString(controller.players.last) + statsString(controller.players.head) + firstOutputString()
  }

  def endOfGameStats(): String = {
    val res = controller.players.sortBy(_.cardCount).reverse
    val builder = new StringBuilder
    var looseList:List[PlayerInterface] = Nil
    for (pl <- res) if (pl.cardCount == res.last.cardCount) looseList = looseList.::(pl.setLife(pl.life - 1))
    controller.players = res.dropRight(looseList.size)
    for (pl <- looseList) {
      if (pl.life - 1 == -1) builder.append(pl.name).append(" you're out") else
        controller.players = controller.players.::(pl.setLife(pl.life - 1))
    }
    for (pl <- res) builder.append(s"\n${pl.name}:    ${pl.cardCount} points    ${pl.life} lives left")
    builder.append("\n")
    for (pl <- looseList) builder.append(pl.name).append(", ")
    builder.append("lost a Life").append("\nstart next round with(nr)")
    builder.toString()
  }
}
