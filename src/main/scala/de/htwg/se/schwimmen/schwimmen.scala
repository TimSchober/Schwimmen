package de.htwg.se.schwimmen

import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.aUI.{GUI, TUI}
import de.htwg.se.schwimmen.controller.controllerComponent._

import scala.io.StdIn.readLine

object schwimmen {

  val injector: Injector = Guice.createInjector(new schwimmenModul)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  controller.createNewGame()
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
