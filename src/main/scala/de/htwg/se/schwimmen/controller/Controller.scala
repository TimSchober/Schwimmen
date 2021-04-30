package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.CardStack
import de.htwg.se.schwimmen.util.Observable

class Controller(var stack: CardStack) extends Observable {
  def createCardStack(): Unit = {
    stack = new CardStack
    notifyObservers
  }
  def stackPrint(): String = stack.toString
}
