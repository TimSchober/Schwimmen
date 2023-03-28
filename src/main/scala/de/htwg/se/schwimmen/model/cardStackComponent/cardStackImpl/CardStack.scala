package de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl

import com.google.inject.Inject
import de.htwg.se.schwimmen.model.cardStackComponent._

import scala.util.Random

case class CardStack @Inject() (rndCardStack: List[(String, String)] = Nil) extends CardStackInterface{

  def setCardStack(): CardStack = {
    val num = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
    val col = List("heart", "diamond", "spade", "club")
    val fullCardStack: List[(String, String)] = num.flatMap(n => col.map(x => (n, x)))
    copy(rndCardStack = Random.shuffle(fullCardStack))
  }

  def getThreeCards: List[(String, String)] = {
    rndCardStack.take(3)
  }

  def delThreeCards: CardStack = {
    copy(rndCardStack.drop(3))
  }
}
