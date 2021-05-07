package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.Player
import de.htwg.se.schwimmen.util.Observable

import scala.io.StdIn.readLine

class TUI(var controller: Controller) extends Observable {

  def gamestart(plAmount: Int, plList: List[String]): Int = {
    controller.createCardStack()
    controller.createField()
    if (!controller.field.processPlayerAmount(plAmount))
      return -1
    controller.playerAmount = plAmount
    controller.createPlayers(plList)
    0
  }

  def sayWelcome(): String = {
    """Welcome to Schwimmen!
      |How many players want to play?
      |Possible player amount is 2-9.
      |""".stripMargin
  }

  def processInput(currentPlayer: Player, answer: String): String = {
    answer match {
      case "y" => "which one of yours?(1/2/3)"
      case "all" =>
        currentPlayer.cardsOnHand = controller.field.swapAllCards(currentPlayer.cardsOnHand)
        "nextTurn"
      case "k" =>
        currentPlayer.hasKnocked = true
        "nextTurn"
      case "n" => "nextTurn"
      case _ => "illegal input, try again"
    }
  }
  def processInput2(currentPlayer: Player, playerCardNr: Int, fieldCardNr: Int): Unit = {
    currentPlayer.cardsOnHand = controller.field.swapCard(currentPlayer.cardsOnHand, playerCardNr - 1, fieldCardNr - 1)
  }
}
