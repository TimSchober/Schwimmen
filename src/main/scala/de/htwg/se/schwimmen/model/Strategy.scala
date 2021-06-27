package de.htwg.se.schwimmen.model

import de.htwg.se.schwimmen.model.fieldComponent._

trait Strategy {
  def checkStop(player: PlayerInterface):Boolean
}
