package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import de.htwg.se.schwimmen.util.Command

class KnockCommand (controller: Controller) extends Command {
  override def doStep: Unit = {
    controller.players = controller.players.patch(0, Seq(controller.players.head.setHasKnocked(true)), 1)
  }

  override def undoStep: Unit = {
    controller.undoStep()
  }

  override def redoStep: Unit = {
    controller.setKnocked()
  }
}
