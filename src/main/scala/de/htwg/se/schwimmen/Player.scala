package de.htwg.se.schwimmen

import scala.util.Random

case class Player(name: String) {

  override def toString: String = name

  val cardsNumber: List[String] = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
  val cardsColour: List[String] = List("heart", "diamond", "spade", "club")

  val cards = for {
    n <- cardsNumber
    c <- cardsColour
  } yield (n, c)

  //Anfang jedes spiel wird eine Liste von Carten generiert
  val randomCards = Random.shuffle(cards)

  def myCard: Unit = {
    val mayCards = randomCards.take(3)    //jeder Spieler zieht sich drei SpielCarten ab
    removeFirstThreeCards(randomCards)    //nach jeder Abziehung werden die drei ersten SpielCarten gelöcht
  }

  def removeFirstThreeCards[A](xs: Iterable[A]) = {
    xs.drop(3).dropRight(0)
  }


}