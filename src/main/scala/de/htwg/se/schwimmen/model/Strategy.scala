package de.htwg.se.schwimmen.model

import de.htwg.se.schwimmen.fieldComponent.PlayerInterface
import de.htwg.se.schwimmen.fieldComponent.*

trait Strategy {
  def checkStop(player: PlayerInterface):Boolean
}
