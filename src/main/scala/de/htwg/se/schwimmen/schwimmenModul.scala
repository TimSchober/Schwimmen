package de.htwg.se.schwimmen

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.schwimmen.controller.controllerComponent.*
import net.codingwell.scalaguice.ScalaModule

class schwimmenModul extends  AbstractModule with ScalaModule {

  override def configure(): Unit = {

    bind[ControllerInterface].to[controllerImpl.Controller]

    bind[List[(String, String)]].toInstance(List[(String, String)]())

  }
}
