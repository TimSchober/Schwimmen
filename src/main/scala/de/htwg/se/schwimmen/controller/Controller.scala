package de.htwg.se.schwimmen.controller

import de.htwg.se.schwimmen.model.{CardStack, Player, PlayingField}
import de.htwg.se.schwimmen.util.UndoManager

import scala.swing.Publisher

class Controller(
                  var stack: CardStack,
                  var players: List[Player],
                  var field: PlayingField,
                  var playerAmount: Int) extends Publisher {

  val undoManager = new UndoManager
  var playerStack: List[Player] = List[Player]()
  var fieldStack: List[PlayingField] = List[PlayingField]()

  def createNewGame(): Unit = {
    stack = CardStack()
    field = PlayingField()
    field = field.setCardsOnField(stack.getThreeCards)
    stack = stack.delThreeCards
    fieldStack = fieldStack.::(field)
    publish(new NewGame)
  }

  def nextRound(): Unit = {
    stack = CardStack()
    field = PlayingField()
    field = field.setCardsOnField(stack.getThreeCards)
    stack = stack.delThreeCards
    fieldStack = Nil.::(field)
    playerStack = Nil
    var newPlayers: List[Player] = Nil
    for (pl <- players) {
      var newPlayer = Player(pl.name, life = pl.life)
      newPlayer = newPlayer.setCardsOnHand(stack.getThreeCards)
      stack = stack.delThreeCards
      newPlayers = newPlayers :+ newPlayer
    }
    players = newPlayers
    publish(new PlayerAdded)
  }

  def setPlayerAmount(plAm: Int): Unit = {
    playerAmount = plAm
    publish(PlayerAmountChanged(plAm))
  }

  def addPlayer(name: String): Unit = {
    var pl = Player(name)
    pl = pl.setCardsOnHand(stack.getThreeCards)
    stack = stack.delThreeCards
    players = players :+ pl
    publish(new PlayerAdded)
  }

  def yesSelected(): Boolean = {
    publish(new YesSelected)
    true
  }
  def cardSelected(): Boolean = {
    publish(new CardSelected)
    true
  }

  var indexStack: List[Int] = List[Int]()
  def swapCards(indexplayer: Int, indexfield: Int): Unit = {
    indexStack = indexStack.::(indexplayer)
    indexStack = indexStack.::(indexfield)
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new SwapCommand(indexplayer, indexfield, this))
    publish(new CardsChanged)
  }

  def swapAllCards(): Unit = {
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new SwapAllCommand( this))
    publish(new CardsChanged)
  }

  def nextPlayer(): Unit = {
    players = players :+ players.head
    players = players.drop(1)
    publish(new PlayerChanged)
  }

  def setKnocked(): Unit = {
    playerStack = playerStack.::(players.head)
    fieldStack = fieldStack.::(field)
    undoManager.doStep(new KnockCommand( this))
    publish(new KnockedChanged)
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
    publish(new PlayerChanged)
  }
  def redo(): Unit = {
    undoManager.redoStep()
    publish(new PlayerChanged)
  }
}
