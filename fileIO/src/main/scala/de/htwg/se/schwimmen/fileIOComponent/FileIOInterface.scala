package de.htwg.se.schwimmen.fileIOComponent

import de.htwg.se.schwimmen.fieldComponent.{PlayerInterface, PlayingFieldInterface}

trait FileIOInterface {

  def loadPlayers: List[PlayerInterface]
  def loadField: PlayingFieldInterface
  def save(players: List[PlayerInterface], field: PlayingFieldInterface): Unit

}
