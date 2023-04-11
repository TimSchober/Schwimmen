package de.htwg.se.schwimmen.cardStackComponent.fileIOComponent

import de.htwg.se.schwimmen.cardStackComponent.fieldComponent.{PlayerInterface, PlayingFieldInterface}

import scala.util.Try

trait FileIOInterface {

  def loadPlayers: List[PlayerInterface]
  def loadField: PlayingFieldInterface
  def save(players: List[PlayerInterface], field: PlayingFieldInterface): Unit

}
