package de.htwg.se.schwimmen.model

import de.htwg.se.schwimmen.{EasyStrategy, FireStrategy}
import de.htwg.se.schwimmen.cardStackComponent.fieldComponent.fieldImpl.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StrategySpec extends AnyWordSpec with Matchers {

  "A strategy" should {
    val easyStrat = new EasyStrategy
    val fireStrat = new FireStrategy
    "be true if a player has 31 points" in {
      var pl = Player("Tim")
      pl = pl.setCardsOnHand(List(("jack", "heart"), ("king", "heart"), ("queen", "heart")))
      easyStrat.checkStop(pl) should be(false)
      fireStrat.checkStop(pl) should be(false)
      pl = pl.setCardsOnHand(List(("ace", "heart"), ("king", "heart"), ("queen", "heart")))
      easyStrat.checkStop(pl) should be(true)
      fireStrat.checkStop(pl) should be(true)
    }
    "have a different outcome weather player chooses easy or fire strat" in {
      var pl = Player("Tim")
      pl = pl.setCardsOnHand(List(("ace", "heart"), ("ace", "spade"), ("ace", "diamond")))
      easyStrat.checkStop(pl) should be(false)
      fireStrat.checkStop(pl) should be(true)
    }
  }

}
