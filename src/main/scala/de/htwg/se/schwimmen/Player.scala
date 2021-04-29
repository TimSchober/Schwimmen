package de.htwg.se.schwimmen

case class Player(name: String) {

  override def toString: String = name

  var life = 3
}