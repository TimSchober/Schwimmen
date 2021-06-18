package de.htwg.se.schwimmen

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.schwimmen.controller.controllerComponent._
import de.htwg.se.schwimmen.model.cardStackComponent._
import de.htwg.se.schwimmen.model.cardStackComponent.cardStackImpl.CardStack
import de.htwg.se.schwimmen.model.fieldComponent._
import de.htwg.se.schwimmen.model.fieldComponent.fieldImpl.{Player, PlayingField}
import net.codingwell.scalaguice.ScalaModule

class schwimmenModul extends  AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("hasKnocked")).to(false)
    bindConstant().annotatedWith(Names.named("plAm")).to(0)
    bindConstant().annotatedWith(Names.named("cardCount")).to(0.0)
    bindConstant().annotatedWith(Names.named("life")).to(3)

    bind[ControllerInterface].to[controllerImpl.Controller]
    bind[CardStackInterface].to[CardStack]
    bind[PlayingFieldInterface].to[PlayingField]
    bind[PlayerInterface].to[Player]

    bind[List[PlayerInterface]].toInstance(List[Player]())
    bind[List[(String, String)]].toInstance(List[(String, String)]())
  }
}
