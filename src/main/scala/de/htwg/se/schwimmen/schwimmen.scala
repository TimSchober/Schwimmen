package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.aUI.TUI
import de.htwg.se.schwimmen.controller.Controller
import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}

object schwimmen {

  val stack = new CardStack
  val field: PlayingField = PlayingField(stack)
  val players: List[Player] = Nil
  val controller = new Controller(stack, players, field, 0)
  val tui = new TUI(controller)

  def main(args: Array[String]): Unit = {
    tui.gamestart()
  }
}
