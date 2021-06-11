//package de.htwg.se.schwimmen.controller
//
//import de.htwg.se.schwimmen.util.Observer
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.wordspec.AnyWordSpec
//
//class ControllerSpec extends AnyWordSpec with Matchers{
//
//  "A controller observed by an Observer" should {
//    val controller = new Controller(null, Nil, null, 0)
//    "create a stack of cards" in {
//      controller.createCardStack()
//      controller.stack.rndCardStack.length should be(32)
//    }
//    "create a field" in {
//      controller.createField()
//      controller.field.cardsOnField.length should be(3)
//      controller.stack.rndCardStack.length should be(29)
//    }
//    "add a single player" in {
//      controller.playerAmount = 2
//      controller.addPlayer("Tim")
//      controller.players.head.cardsOnHand.length should be(3)
//      controller.stack.rndCardStack.length should be(26)
//      controller.addPlayer("Ayaz")
//      controller.players.last.cardsOnHand.length should be(3)
//      controller.stack.rndCardStack.length should be(23)
//      controller.players.last.name should equal("Ayaz")
//      controller.players.head.name should equal("Tim")
//    }
//    "swap one card of the first player and field" in {
//      val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
//      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
//      controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(playerli)), 1)
//      controller.field = controller.field.setCardsOnField(fieldli)
//      controller.swapCards(0, 0)
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("jack","spade"),("queen","spade")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("8","hearts"),("9","hearts")))
//    }
//    "swap all cards of the first player and the field" in {
//      val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
//      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
//      controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(playerli)), 1)
//      controller.field = controller.field.setCardsOnField(fieldli)
//      controller.swapAllCards()
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("8","hearts"),("9","hearts")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("jack","spade"),("queen","spade")))
//    }
//    "get the next player" in {
//      controller.nextPlayer()
//      controller.players.head.name should equal("Ayaz")
//    }
//    "set a player to hasKnocked = true" in {
//      controller.setKnocked()
//      controller.players.head.hasKnocked should be(true)
//    }
//    "undo the last knock round" in {
//      controller.undo()
//      controller.players.head.hasKnocked should be(false)
//    }
//    "redo the last knock round" in {
//      controller.redo()
//      controller.players.head.hasKnocked should be(true)
//    }
//    "undo the last swap round" in {
//      val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
//      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
//      controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(playerli)), 1)
//      controller.field = controller.field.setCardsOnField(fieldli)
//      controller.swapCards(0, 0)
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("jack","spade"),("queen","spade")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("8","hearts"),("9","hearts")))
//      controller.undo()
//      controller.players.head.cardsOnHand should equal(List(("10","spade"),("jack","spade"),("queen","spade")))
//      controller.field.cardsOnField should equal(List(("7","hearts"),("8","hearts"),("9","hearts")))
//    }
//    "redo the last swap round" in {
//      controller.redo()
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("jack","spade"),("queen","spade")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("8","hearts"),("9","hearts")))
//    }
//    "undo the last swapAll round" in {
//      val playerli = List(("10","spade"),("jack","spade"),("queen","spade"))
//      val fieldli = List(("7","hearts"),("8","hearts"),("9","hearts"))
//      controller.players = controller.players.patch(0, Seq(controller.players.head.setCardsOnHand(playerli)), 1)
//      controller.field = controller.field.setCardsOnField(fieldli)
//      controller.swapAllCards()
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("8","hearts"),("9","hearts")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("jack","spade"),("queen","spade")))
//      controller.undo()
//      controller.players.head.cardsOnHand should equal(List(("10","spade"),("jack","spade"),("queen","spade")))
//      controller.field.cardsOnField should equal(List(("7","hearts"),("8","hearts"),("9","hearts")))
//    }
//    "redo the last swapAll round" in {
//      controller.redo()
//      controller.players.head.cardsOnHand should equal(List(("7","hearts"),("8","hearts"),("9","hearts")))
//      controller.field.cardsOnField should equal(List(("10","spade"),("jack","spade"),("queen","spade")))
//    }
//    "undo a skipped round" in {
//      controller.players.head.name should be("Ayaz")
//      controller.doNothing()
//      controller.players.head.name should be("Ayaz")
//      controller.undo()
//      controller.players.head.name should be("Ayaz")
//    }
//    "redo a skipped round" in {
//      controller.redo()
//      controller.players.head.name should be("Ayaz")
//    }
//  }
//}
