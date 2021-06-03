package de.htwg.se.schwimmen.model

trait Strategy {
  def checkStop(player: Player):Boolean
}
