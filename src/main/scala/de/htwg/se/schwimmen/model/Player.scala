package de.htwg.se.schwimmen.model

case class Player(name: String, stack: CardStack, cardsOnHand: List[(String, String)] = List(("","")), hasKnocked: Boolean = false, life: Int = 3) {

  def setCardsOnHand(): List[(String, String)] = {
    copy(cardsOnHand = stack.getThreeCards).cardsOnHand
  }
  def setLife(l: Int): Int = {
    copy(life = l).life
  }
  def setHasKnocked(h: Boolean): Boolean = {
    copy(hasKnocked = h).hasKnocked
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
