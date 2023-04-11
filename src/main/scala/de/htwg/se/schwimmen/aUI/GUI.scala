package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.controllerComponent._

import java.io.File
import javax.imageio.ImageIO
import scala.swing._

class GUI(controller: ControllerInterface) extends Frame {

  listenTo(controller)
  title = "Schwimmen"

  def paintCards: Panel = new Panel {
    override def paint(g:Graphics2D): Unit = {
      g.drawImage(ImageIO.read(new File(getPath(controller.field.cardsOnField.head))),200,50,null)
      g.drawImage(ImageIO.read(new File(getPath(controller.field.cardsOnField(1)))),360,50,null)
      g.drawImage(ImageIO.read(new File(getPath(controller.field.cardsOnField.last))),520,50,null)
      g.drawImage(ImageIO.read(new File(getPath(controller.players.head.cardsOnHand.head))),200,300,null)
      g.drawImage(ImageIO.read(new File(getPath(controller.players.head.cardsOnHand(1)))),360,300,null)
      g.drawImage(ImageIO.read(new File(getPath(controller.players.head.cardsOnHand.last))),520,300,null)
    }
  }

  var fieldIndex = 1
  var handIndex = 1
  def buttons: GridPanel = new GridPanel(2,6) {
    contents += Button("field(1)") {fieldIndex = 1}
    contents += Button("field(2)") {fieldIndex = 2}
    contents += Button("field(3)") {fieldIndex = 3}
    contents += Button("hand(1)") {handIndex = 1}
    contents += Button("hand(2)") {handIndex = 2}
    contents += Button("hand(3)") {handIndex = 3}
    contents += new Label(controller.players.head.name.toString)
    val changeOneCardButton: Button = Button("ChangeOneCard") {
      controller.swapCards(handIndex - 1, fieldIndex - 1)
      controller.nextPlayer()
    }
    val changeAllCardButton: Button = Button("ChangeAllCards") {
      controller.swapAllCards()
      controller.nextPlayer()
    }
    val knockButton: Button = Button("Knock") {
      controller.setKnocked()
      controller.nextPlayer()
    }
    val doNothingButton: Button = Button("Skip") {
      controller.doNothing()
      controller.nextPlayer()
    }
    contents += changeOneCardButton
    contents += changeAllCardButton
    contents += knockButton
    contents += doNothingButton
  }

  reactions += {
    case event: PlayerAdded => redraw()
    case event: PlayerChanged => redraw()
  }

  menuBar = new MenuBar {
    contents += new Menu("Edit") {
      contents += new MenuItem(Action("Undo") { controller.undo() })
      contents += new MenuItem(Action("Redo") { controller.redo() })
    }
  }

  def redraw(): Unit = {
    contents = new BorderPanel {
      add(paintCards, BorderPanel.Position.Center)
      add(buttons, BorderPanel.Position.South)
    }
  }

  preferredSize = new Dimension(1000,700)
  visible = true

  def getPath(card: (String,String)): String = {
    "src/main/scala/de/htwg/se/schwimmen/aUI/textures/" + {
      card._2 match {
        case "club" =>
          card._1 match {
            case "7" => "7_of_clubs.png"
            case "8" => "8_of_clubs.png"
            case "9" => "9_of_clubs.png"
            case "10" => "10_of_clubs.png"
            case "jack" => "jack_of_clubs.png"
            case "queen" => "queen_of_clubs.png"
            case "king" => "king_of_clubs.png"
            case "ace" => "ace_of_clubs.png"
          }
        case "diamond" =>
          card._1 match {
            case "7" => "7_of_diamonds.png"
            case "8" => "8_of_diamonds.png"
            case "9" => "9_of_diamonds.png"
            case "10" => "10_of_diamonds.png"
            case "jack" => "jack_of_diamonds.png"
            case "queen" => "queen_of_diamonds.png"
            case "king" => "king_of_diamonds.png"
            case "ace" => "ace_of_diamonds.png"
          }
        case "heart" =>
          card._1 match {
            case "7" => "7_of_hearts.png"
            case "8" => "8_of_hearts.png"
            case "9" => "9_of_hearts.png"
            case "10" => "10_of_hearts.png"
            case "jack" => "jack_of_hearts.png"
            case "queen" => "queen_of_hearts.png"
            case "king" => "king_of_hearts.png"
            case "ace" => "ace_of_hearts.png"
          }
        case "spade" =>
          card._1 match {
            case "7" => "7_of_spades.png"
            case "8" => "8_of_spades.png"
            case "9" => "9_of_spades.png"
            case "10" => "10_of_spades.png"
            case "jack" => "jack_of_spades.png"
            case "queen" => "queen_of_spades.png"
            case "king" => "king_of_spades.png"
            case "ace" => "ace_of_spades.png"
          }
      }
    }
  }
}
