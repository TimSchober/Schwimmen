package de.htwg.se.schwimmen.model

import de.htwg.se.schwimmen.fieldComponent.PlayerInterface

class FireStrategy extends Strategy {
  override def checkStop(player: PlayerInterface): Boolean = {
    val valueList = player.getValuesOfCards(player.cardsOnHand)
    (valueList.head == 11 && valueList(1) == 11 && valueList.last == 11) || player.cardCount == 31
  }
}
