package de.htwg.se.schwimmen

import scala.util.Random

case class CardStack(rndCards: List[(String, String)]) {

  val cardsNumber: List[String] = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
  val cardsColour: List[String] = List("heart", "diamond", "spade", "club")

  def getFullCardStack: List[(String, String)] = for {
    n <- cardsNumber
    c <- cardsColour
  } yield (n, c)


  //var rndCards = Random.shuffle(getFullCardStack)
//
//  def myCard: Unit = {
//    val myCards = randomCards.take(3)
//    removeFirstThreeCards(randomCards)
//  }
//
//  def removeFirstThreeCards[A](xs: Iterable[A]) = {
//    xs.drop(3).dropRight(0)
//  }
}
