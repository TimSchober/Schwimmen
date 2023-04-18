package de.htwg.se.schwimmen.fileIOComponent

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.schwimmen.fieldComponent.fieldImpl.{Player, PlayingField}
import de.htwg.se.schwimmen.fieldComponent.{PlayerInterface, PlayingFieldInterface}
import de.htwg.se.schwimmen.fileIOComponent.fileIOJsonImpl.FileIO
import de.htwg.se.schwimmen.fileIOComponent.{FileIOInterface, fileIOXmlImpl}
import de.htwg.se.schwimmen.fieldComponent.*
import de.htwg.se.schwimmen.fileIOComponent.*
import net.codingwell.scalaguice.ScalaModule

class schwimmenModul extends  AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("hasKnocked")).to(false)
    bindConstant().annotatedWith(Names.named("plAm")).to(0)
    bindConstant().annotatedWith(Names.named("cardCount")).to(0.0)
    bindConstant().annotatedWith(Names.named("life")).to(3)

    bind[PlayingFieldInterface].to[PlayingField]
    bind[PlayerInterface].to[Player]

    bind[Option[String]].toInstance(None)
    bind[List[PlayerInterface]].toInstance(List[Player]())
    bind[List[(String, String)]].toInstance(List[(String, String)]())

    bind[FileIOInterface].annotatedWithName("Xml").to[fileIOXmlImpl.FileIO]
    bind[FileIOInterface].annotatedWithName("Json").to[FileIO]
  }
}
