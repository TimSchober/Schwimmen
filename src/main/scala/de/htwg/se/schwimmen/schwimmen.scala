package de.htwg.se.schwimmen

import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.aUI.{GUI, TUI}
import de.htwg.se.schwimmen.controller.controllerComponent._

import scala.io.StdIn.readLine

object schwimmen {

  val injector: Injector = Guice.createInjector(new schwimmenModul)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new TUI(controller)
  controller.createNewGame()
  // val gui = new GUI(controller)

  def main(args: Array[String]): Unit = {
    while
      (tui.input != "q")
    do
      tui.input = readLine()
      tui.processInput()
  }
}
