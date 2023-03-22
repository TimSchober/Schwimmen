package de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl

import com.google.inject.Inject
import de.htwg.se.schwimmen.model.cardStackComponent._

import scala.util.Random

case class CardStack @Inject() (rndCardStack: List[(String, String)] = Nil) extends CardStackInterface{

  def setCardStack(): CardStack = {
    val fullCardStack: List[(String, String)] = for {
      n <- List("7", "8", "9", "10", "jack", "queen", "king", "ace")
      c <- List("heart", "diamond", "spade", "club")
    } yield (n, c)
    val rndFullCardStack = Random.shuffle(fullCardStack)
    copy(rndCardStack = rndFullCardStack)
  }

  def getThreeCards: List[(String, String)] = {
    rndCardStack.take(3)
  }

  def delThreeCards: CardStack = {
    copy(rndCardStack.drop(3))
  }
}
