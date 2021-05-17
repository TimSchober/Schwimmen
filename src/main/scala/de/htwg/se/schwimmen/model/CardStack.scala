package de.htwg.se.schwimmen.model

import scala.util.Random

case class CardStack() {

  val cardsNumber: List[String] = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
  val cardsColour: List[String] = List("heart", "diamond", "spade", "club")

  var rndCardStack: List[(String, String)] = {
    val fullCardStack: List[(String, String)] = for {
      n <- cardsNumber
      c <- cardsColour
    } yield (n, c)
    Random.shuffle(fullCardStack)
  }

  def getThreeCards: List[(String, String)] = {
    val i = rndCardStack.take(3)
    rndCardStack = rndCardStack.drop(3)
    i
  }
}
