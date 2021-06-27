package de.htwg.se.schwimmen.model.fileIOComponent.fileIOJsonImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import de.htwg.se.schwimmen.model.fieldComponent.fieldImpl.{Player, PlayingField}
import de.htwg.se.schwimmen.model.fileIOComponent.FileIOInterface
import de.htwg.se.schwimmen.schwimmenModul
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec with Matchers{
  "A FileIO" should {
    val injector: Injector = Guice.createInjector(new schwimmenModul)
    "save a Player List and a Field" in {
      var pl1 = Player("Tim")
      var pl2 = Player("Ayaz")
      val field = PlayingField(List(("7","diamond"),("8","diamond"),("9","diamond")))
      pl1 = pl1.setCardsOnHand(List(("10","spade"),("jack","spade"),("queen","spade")))
      pl2 = pl2.setCardsOnHand(List(("7","hearts"),("8","hearts"),("9","hearts")))
      val fileIO = injector.instance[FileIOInterface](Names.named("Json"))
      fileIO.save(List(pl1,pl2), field)
      fileIO.loadField should be(field)
      fileIO.loadPlayers should be(List(pl1,pl2))
    }
  }
}