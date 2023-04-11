package de.htwg.se.schwimmen.model.fieldComponent

trait PlayingFieldInterface {
  def cardsOnField: List[(String, String)]

  def setCardsOnField(threeCards: List[(String, String)]): PlayingFieldInterface
  def swapCard(player: PlayerInterface, indexPlayer: Int, indexField: Int): List[(String, String)]
  def processPlayerAmount(amount: Int): Boolean
  def toString: String
}

trait PlayerInterface {
  def name: Option[String]
  def cardsOnHand: List[(String, String)]
  def cardCount: Double
  def hasKnocked: Boolean
  def life: Int

  def setName(nameString: String): PlayerInterface
  def setCardsOnHand(threeCards: List[(String, String)]): PlayerInterface
  def setLife(l: Int): PlayerInterface
  def setHasKnocked(h: Boolean): PlayerInterface
  def swapCard(field: PlayingFieldInterface, indexPlayer: Int, indexField: Int): List[(String, String)]
  def getColoursOfCards(threeCards: List[(String, String)]): List[String]
  def getValuesOfCards(threeCards: List[(String, String)]): List[Int]
  def getValue(value: String): Int
  def setCardCount(threeCards: List[(String, String)]): Double
  def toString: String
}
