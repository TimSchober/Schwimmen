package de.htwg.se.schwimmen.cardStackComponent.fileIOComponent.fileIOXmlImpl

import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.cardStackComponent.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.cardStackComponent.fileIOComponent.FileIOInterface
import de.htwg.se.schwimmen.schwimmenModul
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOInterface{

  override def loadPlayers: List[PlayerInterface] = {
    val injector: Injector = Guice.createInjector(new schwimmenModul)
    val file = scala.xml.XML.loadFile("field.xml")
    var players: List[PlayerInterface] = Nil
    val playersFromXml = (file \\ "player")
    playersFromXml.foreach {
      pl => {
      val name: String = (pl \ "@name").text
      val cardsOnHand: List[(String, String)] =
        List(((pl \ "cardsOnHandOneVal").text, (pl \ "cardsOnHandOneCol").text),
          ((pl \ "cardsOnHandTwoVal").text, (pl \ "cardsOnHandTwoCol").text),
          ((pl \ "cardsOnHandThreeVal").text, (pl \ "cardsOnHandThreeCol").text))
      val hasKnocked: Boolean = (pl \ "hasKnocked").text.toBoolean
      val life: Int = (pl \ "life").text.toInt
      var player = injector.instance[PlayerInterface]
      player = player.setName(name)
      player = player.setCardsOnHand(cardsOnHand)
      player = player.setHasKnocked(hasKnocked)
      player = player.setLife(life)
      players = players :+ player
      }
    }
    players
  }

  override def loadField: PlayingFieldInterface = {
    val injector: Injector = Guice.createInjector(new schwimmenModul)
    val file = scala.xml.XML.loadFile("field.xml")
    var field = injector.instance[PlayingFieldInterface]
    val fieldFromXml = (file \\ "field")
    field = field.setCardsOnField(
      List(((fieldFromXml \ "cardsOnFieldOneVal").text, (fieldFromXml \ "cardsOnFieldOneCol").text),
        ((fieldFromXml \ "cardsOnFieldTwoVal").text, (fieldFromXml \ "cardsOnFieldTwoCol").text),
        ((fieldFromXml \ "cardsOnFieldThreeVal").text, (fieldFromXml \ "cardsOnFieldThreeCol").text)))
    field
  }

  override def save(players: List[PlayerInterface], field: PlayingFieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(toXml(players, field))
    pw.write(xml)
    pw.close()
  }

  def playerToXml(player: PlayerInterface): Elem = {
    <player name={player.name}>
      <cardsOnHandOneVal>{player.cardsOnHand.head._1}</cardsOnHandOneVal>
      <cardsOnHandOneCol>{player.cardsOnHand.head._2}</cardsOnHandOneCol>
      <cardsOnHandTwoVal>{player.cardsOnHand(1)._1}</cardsOnHandTwoVal>
      <cardsOnHandTwoCol>{player.cardsOnHand(1)._2}</cardsOnHandTwoCol>
      <cardsOnHandThreeVal>{player.cardsOnHand.last._1}</cardsOnHandThreeVal>
      <cardsOnHandThreeCol>{player.cardsOnHand.last._2}</cardsOnHandThreeCol>
      <hasKnocked>{player.hasKnocked.toString}</hasKnocked>
      <life>{player.life.toString}</life>
    </player>
  }
  def toXml(players: List[PlayerInterface], field: PlayingFieldInterface): Elem = {
    <field>
      {
        players.foreach(pl => playerToXml(pl))
      }
      <cardsOnFieldOneVal>{field.cardsOnField.head._1}</cardsOnFieldOneVal>
      <cardsOnFieldOneCol>{field.cardsOnField.head._2}</cardsOnFieldOneCol>
      <cardsOnFieldTwoVal>{field.cardsOnField(1)._1}</cardsOnFieldTwoVal>
      <cardsOnFieldTwoCol>{field.cardsOnField(1)._2}</cardsOnFieldTwoCol>
      <cardsOnFieldThreeVal>{field.cardsOnField.last._1}</cardsOnFieldThreeVal>
      <cardsOnFieldThreeCol>{field.cardsOnField.last._2}</cardsOnFieldThreeCol>
    </field>
  }
}
