package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import de.htwg.se.schwimmen.controller.controllerComponent._
import de.htwg.se.schwimmen.model.cardStackComponent._
import de.htwg.se.schwimmen.model.fieldComponent._

import de.htwg.se.schwimmen.util.UndoManager
import de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl.CardStack
import de.htwg.se.schwimmen.model.fieldComponent.fieldImpl.{Player, PlayingField}

import scala.swing.Publisher

class Controller(
                  var stack: CardStackInterface,
                  var players: List[PlayerInterface],
                  var field: PlayingFieldInterface,
                  var playerAmount: Int) extends ControllerInterface with Publisher {

  val undoManager = new UndoManager
  var playerStack: List[PlayerInterface] = List[PlayerInterface]()
  var fieldStack: List[PlayingFieldInterface] = List[PlayingFieldInterface]()

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
    var newPlayers: List[PlayerInterface] = Nil
    for (pl <- players) {
      var newPlayer:PlayerInterface = Player(pl.name, life = pl.life)
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
    var pl:PlayerInterface = Player(name)
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
