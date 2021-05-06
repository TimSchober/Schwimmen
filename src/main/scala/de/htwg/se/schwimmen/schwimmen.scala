package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.TUI
import de.htwg.se.schwimmen.controller.Controller

object schwimmen {

  val controller = new Controller(null, Nil, null, 0)
  val tui = new TUI(controller)

  def main(args: Array[String]): Unit = {
    tui.gamestart()
  }
}
