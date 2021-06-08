package de.htwg.se.schwimmen.model

case class Player(name: String, cardsOnHand: List[(String, String)] = Nil, hasKnocked: Boolean = false, life: Int = 3) {

  def setCardsOnHand(threeCards: List[(String, String)]): Player = {
    copy(cardsOnHand = threeCards)
  }
  def setLife(l: Int): Player = {
    copy(life = l)
  }
  def setHasKnocked(h: Boolean): Player = {
    copy(hasKnocked = h)
  }

  def swapCard(field: PlayingField, indexPlayer: Int, indexField: Int): List[(String, String)] =
    indexPlayer match {
      case 0 => List(field.cardsOnField(indexField), cardsOnHand(1), cardsOnHand.last)
      case 1 => List(cardsOnHand.head, field.cardsOnField(indexField), cardsOnHand.last)
      case 2 => List(cardsOnHand.head, cardsOnHand(1), field.cardsOnField(indexField))
    }

  override def toString: String = {
    val builder = new StringBuilder
    builder.append("These are the cards in your hand:    ")
    for (x<-cardsOnHand) {
      builder.append(x._1).append(" of ").append(x._2).append("s    ")
    }
    builder.append("        ").append(name).append(" has ").append(life).append(" lives left").toString()
  }
}
