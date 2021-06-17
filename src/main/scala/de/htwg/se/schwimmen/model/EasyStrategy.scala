package de.htwg.se.schwimmen.model

class EasyStrategy extends Strategy {
  override def checkStop(player: Player): Boolean = player.cardCount == 31
}
