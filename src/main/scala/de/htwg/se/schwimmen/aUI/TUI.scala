package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}

import scala.io.StdIn.readLine

class TUI {

  val stack: CardStack = CardStack()

  def gamestart(): Unit = {
    println(
      """Welcome to Schwimmen!
        |How many players want to play?
        |Possible player amount is 2-9.
        |""".stripMargin)
    val playerAmount: Int = readLine().toInt
    if (!processPlayerAmount(playerAmount)) {
      return
    }
    val field = PlayingField(stack)
    val players: List[Player] = List.tabulate(playerAmount) {
      n => Player(readLine(s"Player ${n + 1}, type your name: "), stack)
    }
    rounds(players, field)
  }
  def processPlayerAmount(amount: Int): Boolean = {
    amount match {
      case 2|3|4|5|6|7|8|9 => true
      case _ => false
    }
  }
  def rounds(players: List[Player], field: PlayingField): Unit = {
    while(true) {
      for(player <- players) {
        if(player.hasKnocked) {
          return //count the end result
        }
        println(field.toString)
        println(player.toString)
        val answer = readLine(s"${player.name}, its your turn! Do you want to change a card?(y/n) or all cards?(all) or knock?(k)")
        answer match {
          case "y" =>
            val playerCardNr = readLine("which one of yours?(1/2/3)").toInt
            val fieldCardNr = readLine("which one of the field?(1/2/3)").toInt
            player.cardsOnHand = field.swapCard(player.cardsOnHand, playerCardNr - 1, fieldCardNr - 1)
          case "all" =>
            player.cardsOnHand = field.swapAllCards(player.cardsOnHand)
          case "k" =>
            player.hasKnocked = true
          case _ => return
        }
        println(field.toString)
        println(player.toString)
      }
    }
  }

}
