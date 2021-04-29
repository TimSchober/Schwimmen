package de.htwg.se.schwimmen

import scala.io.StdIn.readLine
import scala.util.Random
import de.htwg.se.schwimmen.CardStack._

object schwimmen {
  def main(args: Array[String]): Unit = {
    println(
      """Welcome to Schwimmen!
        |How many players want to play?
        |Possible player amount is 2-9.
        |""".stripMargin)
    val playerAmount: Int = readLine().toInt

    val players: List[Player] = List.tabulate(playerAmount) {
      n => Player(readLine(s"Player ${n + 1}, type your name: "))
    }

    //val randomCards = Random.shuffle()

  }

}
