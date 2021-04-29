package de.htwg.se.schwimmen.model

case class Player(name: String, stack: CardStack) {

  override def toString: String = {
    val builder = new StringBuilder
    builder.append("These are the cards in your Hand:\t")
    for (x<-cardsOnHand) {
      builder.append(x._1).append(" of ").append(x._2).append("s\t")
    }
    builder.append("\t\t").append(name).append(" has ").append(life).append(" lives left").toString()
  }

  var life = 3

  var cardsOnHand: List[(String, String)] = stack.getThreeCards
}
