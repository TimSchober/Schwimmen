package de.htwg.se.schwimmen.controller.controllerComponent.controllerImpl

import com.google.inject.name.{Named, Names}
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.schwimmen.cardStackComponent.CardStackInterface
import de.htwg.se.schwimmen.controller.controllerComponent.*
import de.htwg.se.schwimmen.cardStackComponent.*
import de.htwg.se.schwimmen.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.fileIOComponent.FileIOInterface
import de.htwg.se.schwimmen.fieldComponent.*
import de.htwg.se.schwimmen.util.UndoManager
import de.htwg.se.schwimmen.schwimmenModul
import net.codingwell.scalaguice.InjectorExtensions.*

import scala.swing.Publisher

class Controller @Inject() (
                  var stack: CardStackInterface,
                  var players: List[PlayerInterface],
                  var field: PlayingFieldInterface,
                  @Named("plAm") var playerAmount: Int = 0) extends ControllerInterface with Publisher {

  val undoManager = new UndoManager
  var playerStack: List[PlayerInterface] = List()
  var fieldStack: List[PlayingFieldInterface] = List()
  val injector: Injector = Guice.createInjector(new schwimmenModul)

  def createNewGame(): Unit = {
    stack = injector.instance[CardStackInterface]
    stack = stack.setCardStack()
    field = injector.instance[PlayingFieldInterface]
    field = field.setCardsOnField(stack.getThreeCards)
    stack = stack.delThreeCards
    fieldStack = fieldStack.::(field)
    publish(new NewGame)
  }

  def nextRound(): Unit = {
    stack = injector.instance[CardStackInterface]
    stack = stack.setCardStack()
    field = injector.instance[PlayingFieldInterface]
    field = field.setCardsOnField(stack.getThreeCards)
    stack = stack.delThreeCards
    fieldStack = Nil.::(field)
    playerStack = Nil
    var newPlayers: List[PlayerInterface] = Nil
    players.foreach(pl => {
      var newPlayer: PlayerInterface = injector.instance[PlayerInterface]
      val playerName = pl.name match {
        case Some(s) => s
        case None => ""
      }
      newPlayer = newPlayer.setName(playerName)
      newPlayer = newPlayer.setLife(pl.life)
      newPlayer = newPlayer.setCardsOnHand(stack.getThreeCards)
      stack = stack.delThreeCards
      newPlayers = newPlayers :+ newPlayer
    })
//    for (pl <- players) {
//      var newPlayer:PlayerInterface = injector.instance[PlayerInterface]
//      newPlayer = newPlayer.setName(pl.name)
//      newPlayer = newPlayer.setLife(pl.life)
//      newPlayer = newPlayer.setCardsOnHand(stack.getThreeCards)
//      stack = stack.delThreeCards
//      newPlayers = newPlayers :+ newPlayer
//    }
    players = newPlayers
    publish(new PlayerAdded)
  }

  def setPlayerAmount(plAm: Int): Unit = {
    playerAmount = plAm
    publish(PlayerAmountChanged(plAm))
  }

  def addPlayer(name: String): Unit = {
    var pl:PlayerInterface = injector.instance[PlayerInterface]
    pl = pl.setName(name)
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

  def saveTo(str:String): Unit = {
    val fileIO = injector.instance[FileIOInterface](Names.named(str))
    fileIO.save(players, field)
    publish(new PlayerChanged)
  }
  def loadFrom(str:String): Unit = {
    val fileIO = injector.instance[FileIOInterface](Names.named(str))
    field = fileIO.loadField
    players = fileIO.loadPlayers
    playerAmount = players.size
    publish(new PlayerChanged)
  }
}
