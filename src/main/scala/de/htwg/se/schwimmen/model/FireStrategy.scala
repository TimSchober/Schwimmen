package de.htwg.se.schwimmen.model

class FireStrategy extends Strategy {
  override def checkStop(player: Player): Boolean = {
    val valueList = player.getValuesOfCards(player.cardsOnHand)
    (valueList.head == 11 && valueList(1) == 11 && valueList.last == 11) || player.cardCount == 31
  }
}
