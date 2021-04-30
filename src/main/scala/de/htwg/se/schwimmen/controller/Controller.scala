package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import de.htwg.se.schwimmen.util.Observable

class Controller(var stack: CardStack, var players: List[Player], var field: PlayingField, var playerAmount: Int) extends Observable {

  def createCardStack(): Unit = {
    stack = new CardStack
    notifyObservers()
  }

  def createPlayers(names: List[String]): Unit = {
    players = List.tabulate(playerAmount) {
      n => Player(names(n), stack)
    }
    notifyObservers()
  }

  def createField(): Unit = {
    field = PlayingField(stack)
    notifyObservers()
  }

  def sayWelcome(): Unit = {
    println(
      """Welcome to Schwimmen!
        |How many players want to play?
        |Possible player amount is 2-9.
        |""".stripMargin)
    notifyObservers()
  }

  def printPlayer(): Unit = {
    for (player<-players) {
      player.toString
    }
    notifyObservers()
  }
}
