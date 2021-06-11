package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import de.htwg.se.schwimmen.util.{Observable, UndoManager}

class Controller(
                  var stack: CardStack,
                  var players: List[Player],
                  var field: PlayingField,
                  var playerAmount: Int) extends Observable {

  val undoManager = new UndoManager
  var playerStack: List[Player] = List[Player]()
  var fieldStack: List[PlayingField] = List[PlayingField]()

  def createCardStack(): Unit = {
    stack = new CardStack
  }

  def createField(): Unit = {
    field = PlayingField()
    field = field.setCardsOnField(stack.getThreeCards)
    stack = stack.delThreeCards
    fieldStack = fieldStack.::(field)
  }

  def addPlayer(name: String): Unit = {
    var pl = Player(name)
    pl = pl.setCardsOnHand(stack.getThreeCards)
    stack = stack.delThreeCards
    players = players :+ pl
  }

  var indexStack: List[Int] = List[Int]()
  def swapCards(indexplayer: Int, indexfield: Int): Unit = {
    indexStack = indexStack.::(indexplayer)
    indexStack = indexStack.::(indexfield)
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new SwapCommand(indexplayer, indexfield, this))
  }

  def swapAllCards(): Unit = {
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new SwapAllCommand( this))
  }

  def nextPlayer(): Unit = {
    players = players :+ players.head
    players = players.drop(1)
  }

  def setKnocked(): Unit = {
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new KnockCommand( this))
  }

  def doNothing(): Unit = {
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new DoNothingCommand( this))
  }

  def undoStep(): Unit = {
    players = players.reverse :+ playerStack.head
    players = players.drop(1)
    players = players.reverse
    playerStack = playerStack.drop(1)
    field = fieldStack.head
    fieldStack = fieldStack.drop(1)
  }

  def undo(): Unit = {
    undoManager.undoStep()
  }
  def redo(): Unit = {
    undoManager.redoStep()
  }
}
