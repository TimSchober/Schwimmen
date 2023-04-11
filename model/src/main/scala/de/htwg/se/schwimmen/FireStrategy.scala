package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.cardStackComponent.Strategy
import de.htwg.se.schwimmen.cardStackComponent.fieldComponent.PlayerInterface
import de.htwg.se.schwimmen.model.Strategy

class FireStrategy extends Strategy {
  override def checkStop(player: PlayerInterface): Boolean = {
    val valueList = player.getValuesOfCards(player.cardsOnHand)
    (valueList.head == 11 && valueList(1) == 11 && valueList.last == 11) || player.cardCount == 31
  }
}
