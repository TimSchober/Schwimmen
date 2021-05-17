package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import de.htwg.se.schwimmen.util.Observable

class Controller(
                  var stack: CardStack,
                  var players: List[Player],
                  var field: PlayingField,
                  var currentPlayer: Player,
                  var playerAmount: Int) extends Observable {

  def createCardStack(): Unit = {
    stack = new CardStack
  }

  def createPlayers(names: List[String]): Unit = {
    players = List.tabulate(playerAmount) {
      n => Player(names(n), stack)
    }
    currentPlayer = players.head
  }

  def addPlayer(name: String): Unit = {
    players = players :+ Player(name, stack)
    currentPlayer = players.head
  }

  def createField(): Unit = {
    field = PlayingField(stack)
  }

  def nextPlayer(): Unit = {
    currentPlayer = players((players.indexOf(currentPlayer) + 1) % playerAmount)
  }
}
