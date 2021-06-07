package de.htwg.se.schwimmen.model

import scala.util.Random

case class CardStack(rndCardStack: List[(String, String)] = {
  val fullCardStack: List[(String, String)] = for {
    n <- List("7", "8", "9", "10", "jack", "queen", "king", "ace")
    c <- List("heart", "diamond", "spade", "club")
  } yield (n, c)
  Random.shuffle(fullCardStack)
}) {

  def getThreeCards: List[(String, String)] = {
    val i = rndCardStack.take(3)
    copy(rndCardStack.drop(3))
    i
  }
}
