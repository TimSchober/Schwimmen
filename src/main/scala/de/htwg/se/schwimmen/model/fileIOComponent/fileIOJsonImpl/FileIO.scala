package de.htwg.se.schwimmen.model.fileIOComponent.fileIOJsonImpl

import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.model.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.model.fileIOComponent.FileIOInterface
import de.htwg.se.schwimmen.schwimmenModul
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface{
  override def loadPlayers: List[PlayerInterface] = {
    val injector: Injector = Guice.createInjector(new schwimmenModul)
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    var players: List[PlayerInterface] = Nil
    val size: Int = (json \ "size").get.toString().toInt
    (0 to size) foreach {
      val cardsOnHand: List[(String, String)] =
        List(((json \\ "cardsOnHandOneVal").toString(), (json \\ "cardsOnHandOneCol").toString()),
          ((json \\ "cardsOnHandTwoVal").toString(), (json \\ "cardsOnHandTwoCol").toString()),
          ((json \\ "cardsOnHandThreeVal").toString(), (json \\ "cardsOnHandThreeCol").toString()))

      var player = injector.instance[PlayerInterface]
      player = player.setName((json \\ "name").toString())
      player = player.setCardsOnHand(cardsOnHand)
      player = player.setHasKnocked((json \\ "hasKnocked").toString().toBoolean)
      player = player.setLife((json \\ "life").toString().toInt)
      players = players :+ player
      players
    }
    players
  }

  override def loadField: PlayingFieldInterface = {
    val injector: Injector = Guice.createInjector(new schwimmenModul)
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    var field = injector.instance[PlayingFieldInterface]
    field = field.setCardsOnField(
      List(((json \ "field" \ "cardsOnFieldOneVal").get.as[String], (json \ "field" \ "cardsOnFieldOneCol").get.as[String]),
      ((json \ "field" \ "cardsOnFieldTwoVal").get.as[String], (json \ "field" \ "cardsOnFieldTwoCol").get.as[String]),
      ((json \ "field" \ "cardsOnFieldThreeVal").get.as[String], (json \ "field" \ "cardsOnFieldThreeCol").get.as[String])))
    field
  }

  override def save(players: List[PlayerInterface], field: PlayingFieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.json"))
    pw.write(Json.prettyPrint(toJson(players, field)))
    pw.close()
  }
  def toJson(players: List[PlayerInterface], field: PlayingFieldInterface): JsObject = {
    Json.obj(
      "size" -> JsNumber(players.size),
      "players" -> Json.toJson(
        players.map ( p =>
          Json.obj(
            "name" -> Json.toJson(p.name),
            "cardsOnHandOneVal" -> Json.toJson(p.cardsOnHand.head._1),
            "cardsOnHandOneCol" -> Json.toJson(p.cardsOnHand.head._2),
            "cardsOnHandTwoVal" -> Json.toJson(p.cardsOnHand(1)._1),
            "cardsOnHandTwoCol" -> Json.toJson(p.cardsOnHand(1)._2),
            "cardsOnHandThreeVal" -> Json.toJson(p.cardsOnHand.last._1),
            "cardsOnHandThreeCol" -> Json.toJson(p.cardsOnHand.last._2),
            "hasKnocked" -> Json.toJson(p.hasKnocked),
            "life" -> Json.toJson(p.life)
          )
        )
      ),
      "field" -> Json.obj(
        "cardsOnFieldOneVal" -> Json.toJson(field.cardsOnField.head._1),
        "cardsOnFieldOneCol" -> Json.toJson(field.cardsOnField.head._2),
        "cardsOnFieldTwoVal" -> Json.toJson(field.cardsOnField(1)._1),
        "cardsOnFieldTwoCol" -> Json.toJson(field.cardsOnField(1)._2),
        "cardsOnFieldThreeVal" -> Json.toJson(field.cardsOnField.last._1),
        "cardsOnFieldThreeCol" -> Json.toJson(field.cardsOnField.last._2)
      )
    )
  }
}
