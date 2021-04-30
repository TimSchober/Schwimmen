package de.htwg.se.schwimmen.model

case class PlayingField(stack: CardStack) {

  override def toString: String = {
    val builder = new StringBuilder
    builder.append("These are the cards on the field:\t")
    for (x<-cardsOnField) {
      builder.append(x._1).append(" of ").append(x._2).append("s\t")
    }
    builder.toString()
  }

  var cardsOnField: List[(String, String)] = stack.getThreeCards

  def swapCard(playerCards: List[(String, String)], indexPlayer: Int, indexField: Int): List[(String, String)] = {
    val newcardsOnField = indexField match {
      case 0 => List(cardsOnField(1), cardsOnField.last, playerCards(indexPlayer))
      case 1 => List(cardsOnField.head, cardsOnField.last, playerCards(indexPlayer))
      case 2 => List(cardsOnField.head, cardsOnField(1), playerCards(indexPlayer))
    }
    val returnList = indexPlayer match {
      case 0 => List(playerCards(1), playerCards.last, cardsOnField(indexField))
      case 1 => List(playerCards.head, playerCards.last, cardsOnField(indexField))
      case 2 => List(playerCards.head, playerCards(1), cardsOnField(indexField))
    }
    cardsOnField = newcardsOnField
    returnList
  }

  def swapAllCards(playerCards: List[(String, String)]): List[(String, String)] = {
    val returnList = cardsOnField
    cardsOnField = playerCards
    returnList
  }

  def processPlayerAmount(amount: Int): Boolean = {
    amount match {
      case 2|3|4|5|6|7|8|9 => true
      case _ => false
    }
  }
}
