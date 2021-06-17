package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.{GUI, TUI}
import de.htwg.se.schwimmen.controller.{Controller, NewGame}
import de.htwg.se.schwimmen.model.{CardStack, PlayingField}

import scala.io.StdIn.readLine

object schwimmen {

  val controller = new Controller(CardStack(), Nil, PlayingField(), 0)
  val tui = new TUI(controller)
  val gui = new GUI(controller)
  controller.publish(new NewGame)

  def main(args: Array[String]): Unit = {
    do {
      tui.input = readLine()
      tui.processInput()
    } while (tui.input != "q")
  }
}
