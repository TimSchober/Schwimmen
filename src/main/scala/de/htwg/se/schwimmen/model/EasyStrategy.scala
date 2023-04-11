package de.htwg.se.schwimmen.model

import de.htwg.se.schwimmen.fieldComponent.PlayerInterface

class EasyStrategy extends Strategy {
  override def checkStop(player: PlayerInterface): Boolean = player.cardCount == 31
}
