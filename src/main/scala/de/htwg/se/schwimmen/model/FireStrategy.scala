package de.htwg.se.schwimmen.model

class FireStrategy extends Strategy {
  override def checkStop(player: Player): Boolean = true
}