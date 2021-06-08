package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.TUI
import de.htwg.se.schwimmen.controller.Controller

import scala.io.StdIn.readLine

object schwimmen {

  val tui = new TUI(new Controller(null, null, null, null, 0))

  def main(args: Array[String]): Unit = {
    while (true) {
      if (tui.input == "end") {
        return
      }
      tui.controller.notifyObservers()
      if (tui.input != "z" && tui.input != "r") {
        tui.input = readLine()
      } else {
        tui.input = "printStats"
      }
    }
  }
}
