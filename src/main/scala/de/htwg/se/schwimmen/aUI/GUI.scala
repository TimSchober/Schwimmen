package de.htwg.se.schwimmen.aUI

import de.htwg.se.schwimmen.controller.controllerComponent.*
import play.api.libs.json.JsValue

import java.io.File
import javax.imageio.ImageIO
import scala.swing.*

class GUI(controller: ControllerInterface) extends Frame {

  listenTo(controller)
  title = "Schwimmen"

  def paintCards: Panel = new Panel {
    override def paint(g:Graphics2D): Unit = {
      g.drawImage(ImageIO.read(new File(getPath((controller.currentField \ "data" \ "cardsOnField" \ "firstCard").get))),200,50,null)
      g.drawImage(ImageIO.read(new File(getPath((controller.currentField \ "data" \ "cardsOnField" \ "secondCard").get))),360,50,null)
      g.drawImage(ImageIO.read(new File(getPath((controller.currentField \ "data" \ "cardsOnField" \ "thirdCard").get))),520,50,null)
      g.drawImage(ImageIO.read(new File(getPath((controller.headPlayer \ "data" \ "cardsOnHand" \ "firstCard").get))),200,300,null)
      g.drawImage(ImageIO.read(new File(getPath((controller.headPlayer \ "data" \ "cardsOnHand" \ "secondCard").get))),360,300,null)
      g.drawImage(ImageIO.read(new File(getPath((controller.headPlayer \ "data" \ "cardsOnHand" \ "thirdCard").get))),520,300,null)
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
    val playerName: String = (controller.headPlayer \ "data" \ "name").get.toString.replaceAll("\"", "")
    contents += new Label(playerName)
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

  def getPath(card: JsValue): String = {
    "src/main/scala/de/htwg/se/schwimmen/aUI/textures/" + {
      val value = (card \ "Value").get.toString.replaceAll("\"", "")
      val color = (card \ "Color").get.toString.replaceAll("\"", "")
      color match {
        case "club" =>
          value match {
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
          value match {
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
          value match {
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
          value match {
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
