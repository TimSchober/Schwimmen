package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.util.Observer

class TUI(var controller: Controller) extends Observer {

  controller.add(this)
  controller.createCardStack()
  controller.createField()

  def sayWelcome(): String = {
    """Welcome to Schwimmen!
      |How many players want to play?
      |Possible player amount is 2-9.
      |""".stripMargin
  }

  def currentPlayerStats(): String = {
    "\n" + controller.field.toString + "\n" + controller.currentPlayer.toString() + "\n"
  }

  def processPlayerAmountInput(): String = {
    if (!controller.field.processPlayerAmount(input.toInt))
      return "\nillegal input, try again"
    controller.playerAmount = input.toInt
    turn += 1
    "\nPlayer 1, type your name:"
  }

  var n = 1
  def processPlayerNameInput(): String = {
    controller.addPlayer(input)//---
    if (controller.playerAmount <= n) {
      turn += 1
      currentPlayerStats() + firstOutput()
    } else {
      n += 1
      s"\nPlayer $n, type your name:"
    }
  }

  def firstOutput(): String = {
    if(controller.currentPlayer.hasKnocked) {
      turn = -2
      return "end"
    }
    s"\n${controller.currentPlayer.name}, its your turn! " +
      s"Do you want to change a card?(y/n) or all cards?(all) or knock?(k)"
  }

  def processFirstInput(): String = {
    input match {
      case "y" =>
        turn += 1
        "which one of yours?(1/2/3)"
      case "all" =>
        controller.currentPlayer.cardsOnHand = controller.field.swapAllCards(controller.currentPlayer.cardsOnHand)
        turn = 0
        next()
      case "k" =>
        controller.currentPlayer.hasKnocked = true
        turn = 0
        next()
      case "n" =>
        turn = 0
        next()
      case _ => "illegal input, try again"
    }
  }

  def next(): String = {
    val ret = currentPlayerStats()
    controller.nextPlayer()//---
    ret + currentPlayerStats() + firstOutput()
  }

  var playerCardInt = 0
  def processSecondInput(): String = {
    playerCardInt = input.toInt
    turn += 1
    "which one of the field?(1/2/3)"
  }

  def processThirdInput(): String = {
    controller.currentPlayer.cardsOnHand = controller.field.swapCard(
      controller.currentPlayer.cardsOnHand, playerCardInt - 1, input.toInt - 1)
    turn = 0
    next()
  }

  var turn: Int = -2
  var input: String = "sayWelcome"
  def matchInput(): String = {
    if (input == "sayWelcome") {
      return sayWelcome()
    }
    if (input == "") {
      return ""
    }
    turn match {
      case -2 => processPlayerAmountInput()
      case -1 => processPlayerNameInput()
      case 0 => processFirstInput()
      case 1 => processSecondInput()
      case 2 => processThirdInput()
    }
  }

  override def update: Boolean = {
    println(matchInput())
    true
  }
}
