package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.TUI
import de.htwg.se.schwimmen.controller.Controller

import scala.io.StdIn.readLine

object schwimmen {

  val controller = new Controller(null, Nil, null, 0)
  val tui = new TUI(controller)

  def main(args: Array[String]): Unit = {
    println(tui.sayWelcome())
    val playerAmount = readLine().toInt
    if (tui.gamestart(playerAmount, List.tabulate(playerAmount){
      n => readLine(s"Player ${n + 1}, type your name: ")
    }) == -1) {
      return
    }
    while(true) {
      for(player <- tui.controller.players) {
        if(player.hasKnocked) {
          return
        }
        println()
        println(tui.controller.field.toString)
        println(player.toString)
        println()
        var processedInput = ""
        do {
          processedInput = tui.processInput(player, readLine(s"${player.name}, its your turn! " +
            s"Do you want to change a card?(y/n) or all cards?(all) or knock?(k)"))
          println(processedInput)
        } while (processedInput.equals("illegal input, try again"))
        if (!processedInput.equals("nextTurn")) {
          tui.processInput2(player, readLine().toInt, readLine("which one of the field?(1/2/3)").toInt)
        }
        println()
        println(tui.controller.field.toString)
        println(player.toString)
        println()
      }
    }
  }
}
