package de.htwg.se.schwimmen

import de.htwg.se.schwimmen.cardStackComponent.Strategy
import de.htwg.se.schwimmen.cardStackComponent.fieldComponent.PlayerInterface
import de.htwg.se.schwimmen.model.Strategy

class EasyStrategy extends Strategy {
  override def checkStop(player: PlayerInterface): Boolean = player.cardCount == 31
}
