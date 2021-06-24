package de.htwg.se.schwimmen.model.fileIOComponent

import de.htwg.se.schwimmen.model.fieldComponent.{PlayerInterface, PlayingFieldInterface}

trait FileIOInterface {

  def loadPlayers: List[PlayerInterface]
  def loadField: PlayingFieldInterface
  def save(players: List[PlayerInterface], field: PlayingFieldInterface)

}
