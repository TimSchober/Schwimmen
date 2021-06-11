package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.{GUI, TUI}
import de.htwg.se.schwimmen.controller.Controller

import scala.io.StdIn.readLine

object schwimmen {

  val controller = new Controller(null, Nil, null, 0)
  val tui = new TUI(controller)
  val gui = new GUI(controller)
  tui.controller.createNewGame()

  def main(args: Array[String]): Unit = {
    do {
      tui.input = readLine()
      tui.processInput()
    } while (tui.input != "q")
  }
}
