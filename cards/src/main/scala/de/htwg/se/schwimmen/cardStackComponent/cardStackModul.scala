package de.htwg.se.schwimmen.cardStackComponent

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.schwimmen.cardStackComponent.*
import de.htwg.se.schwimmen.cardStackComponent.cardStackImpl.CardStack
import net.codingwell.scalaguice.ScalaModule

class cardStackModul extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[CardStackInterface].to[CardStack]

    bind[List[(String, String)]].toInstance(List[(String, String)]())
  }
}
