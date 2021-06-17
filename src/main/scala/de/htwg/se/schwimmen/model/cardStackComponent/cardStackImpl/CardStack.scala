package de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl

import de.htwg.se.schwimmen.model.cardStackComponent._

import scala.util.Random

case class CardStack(rndCardStack: List[(String, String)] = {
  val fullCardStack: List[(String, String)] = for {
    n <- List("7", "8", "9", "10", "jack", "queen", "king", "ace")
    c <- List("heart", "diamond", "spade", "club")
  } yield (n, c)
  Random.shuffle(fullCardStack)
}) extends CardStackInterface{

  def getThreeCards: List[(String, String)] = {
    rndCardStack.take(3)
  }

  def delThreeCards: CardStack = {
    copy(rndCardStack.drop(3))
  }
}
