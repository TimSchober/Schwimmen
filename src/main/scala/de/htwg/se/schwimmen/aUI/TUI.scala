package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.Player
import de.htwg.se.schwimmen.util.Observable

import scala.io.StdIn.readLine

class TUI(var controller: Controller) extends Observable{

  def gamestart(): Unit = {
    sayWelcome()
    controller.createCardStack()
    controller.playerAmount = readLine().toInt
    controller.createField()
    if (controller.field.processPlayerAmount(controller.playerAmount))
      return
    readPlayers()
    rounds(controller.players)
  }

  def readPlayers(): Unit = {
    controller.createPlayers(List.tabulate(controller.playerAmount){
      n => readLine(s"Player ${n + 1}, type your name: ")
    })
  }

  def sayWelcome(): Unit = {
    println(
      """Welcome to Schwimmen!
        |How many players want to play?
        |Possible player amount is 2-9.
        |""".stripMargin)
  }

  def processInput(currentPlayer: Player): Unit = {
    val answer = readLine(s"${currentPlayer.name}, its your turn! Do you want to change a card?(y/n) or all cards?(all) or knock?(k)")
    answer match {
      case "y" =>
        val playerCardNr = readLine("which one of yours?(1/2/3)").toInt
        val fieldCardNr = readLine("which one of the field?(1/2/3)").toInt
        currentPlayer.cardsOnHand = controller.field.swapCard(currentPlayer.cardsOnHand, playerCardNr - 1, fieldCardNr - 1)
      case "all" =>
        currentPlayer.cardsOnHand = controller.field.swapAllCards(currentPlayer.cardsOnHand)
      case "k" =>
        currentPlayer.hasKnocked = true
      case "n" =>
      case _ =>
        println("illegal input, try again")
        processInput(currentPlayer)
    }
  }

  def rounds(players: List[Player]): Unit = {
    while(true) {
      for(player <- players) {
        if(player.hasKnocked) {
          return //count the end result
        }
        println()
        println(controller.field.toString)
        println(player.toString)
        println()
        processInput(player)
        println()
        println(controller.field.toString)
        println(player.toString)
        println()
      }
    }
  }
}
