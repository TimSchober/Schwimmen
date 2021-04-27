package de.htwg.se.schwimmen

case class GameCard(number: String, colour: String) {

  val cardsNumber: List[String] = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
  val cardsColour: List[String] = List("heart", "diamond", "spade", "club")

  def isCard: Boolean = {
    for (n <- cardsNumber) {
      if (number == n) {
        for (c <- cardsColour)
          if (colour == c)
            return true
      }
    }
    return false
  }

}
