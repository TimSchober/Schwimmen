package de.htwg.se.schwimmen.model

case class Player(name: String, stack: CardStack) {

  override def toString: String = name

  var life = 3

  var cardsOnHand: List[(String, String)] = stack.getThreeCards
}
