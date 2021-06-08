package de.htwg.se.schwimmen.controller

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
