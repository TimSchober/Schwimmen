package de.htwg.se.schwimmen.controller.controllerComponent

import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.model.fieldComponent.*

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  var stack: CardStackInterface
  var players: List[PlayerInterface]
  var field: PlayingFieldInterface
  var playerAmount: Int

  def createNewGame(): Unit
  def nextRound(): Unit
  def setPlayerAmount(plAm: Int): Unit
  def addPlayer(name: String): Unit
  def yesSelected(): Boolean
  def cardSelected(): Boolean
  def swapCards(indexplayer: Int, indexfield: Int): Unit
  def swapAllCards(): Unit
  def nextPlayer(): Unit
  def setKnocked(): Unit
  def doNothing(): Unit
  def undoStep(): Unit
  def undo(): Unit
  def redo(): Unit
  def saveTo(str:String): Unit
  def loadFrom(str:String): Unit
}

import scala.swing.event.Event

class NewGame extends Event
case class PlayerAmountChanged(plAm: Int) extends Event
class PlayerAdded extends Event
class CardsChanged extends Event
class KnockedChanged extends Event
class PlayerChanged extends Event
class YesSelected extends Event
class CardSelected extends Event
