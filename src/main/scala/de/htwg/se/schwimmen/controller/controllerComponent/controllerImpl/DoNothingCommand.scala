package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import de.htwg.se.schwimmen.util.Command

class DoNothingCommand (controller: Controller) extends Command {
  override def doStep: Unit = {
  }

  override def undoStep: Unit = {
    controller.undoStep()
  }

  override def redoStep: Unit = {
    controller.doNothing()
  }
}
