package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import de.htwg.se.schwimmen.util.Command

class SwapCommand (indexPlayer: Int, indexField: Int, controller: Controller) extends Command {
  override def doStep: Unit = {
    val i = controller.players.head.swapCard(controller.field, indexPlayer, indexField)
    val j = controller.field.swapCard(controller.players.head, indexPlayer, indexField)
    controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(i)), 1)
    controller.field = controller.field.setCardsOnField(j)
  }

  override def undoStep: Unit = {
    controller.undoStep()
  }

  override def redoStep: Unit = {
    val iField = controller.indexStack.head
    controller.indexStack = controller.indexStack.drop(0)
    val iPlayer = controller.indexStack.head
    controller.indexStack = controller.indexStack.drop(0)
    controller.swapCards(iPlayer, iField)
  }
}
