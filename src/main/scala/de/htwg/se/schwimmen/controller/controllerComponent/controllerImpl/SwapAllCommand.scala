package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import de.htwg.se.schwimmen.util.Command

class SwapAllCommand (controller: Controller) extends Command {
  override def doStep: Unit = {
//    val i = controller.field
//    controller.field = controller.field.setCardsOnField(controller.players.head.cardsOnHand)
//    controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(i.cardsOnField)), 1)
  }

  override def undoStep: Unit = {
    controller.undoStep()
  }

  override def redoStep: Unit = {
    controller.swapAllCards()
  }
}
