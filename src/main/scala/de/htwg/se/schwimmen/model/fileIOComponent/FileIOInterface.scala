package de.htwg.se.schwimmen.model.fileIOComponent

import de.htwg.se.schwimmen.model.fieldComponent.{PlayerInterface, PlayingFieldInterface}

import scala.util.Try

trait FileIOInterface {

  def loadPlayers: Try[List[PlayerInterface]]
  def loadField: Try[PlayingFieldInterface]
  def save(players: List[PlayerInterface], field: PlayingFieldInterface): Try[Unit]

}
