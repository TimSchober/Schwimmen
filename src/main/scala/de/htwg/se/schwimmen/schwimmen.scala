package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import scala.io.StdIn.readLine

object schwimmen {
  def main(args: Array[String]): Unit = {
    println(
      """Welcome to Schwimmen!
        |How many players want to play?
        |Possible player amount is 2-9.
        |""".stripMargin)
    val playerAmount: Int = readLine().toInt
    val stack = CardStack()
    val field = PlayingField(stack)
    val players: List[Player] = List.tabulate(playerAmount) {
      n => Player(readLine(s"Player ${n + 1}, type your name: "), stack)
    }
    while(true) {
      for(player <- players) {

        println(field.toString)
        println(player.toString)
        val answer = readLine(s"${player.name}, its your turn! Do you want to change a card?(y/n) or all cards?(all) or quit?(q)")
        if (answer == "y") {
          val playerCardNr = readLine("which one of yours?(1/2/3)").toInt
          val fieldCardNr = readLine("which one of the field?(1/2/3)").toInt
          player.cardsOnHand = field.swapCard(player.cardsOnHand, playerCardNr - 1, fieldCardNr - 1)
        } else if (answer == "all") {
          player.cardsOnHand = field.swapAllCards(player.cardsOnHand)
        }
        println(field.toString)
        println(player.toString)
      }
    }

  }
}
