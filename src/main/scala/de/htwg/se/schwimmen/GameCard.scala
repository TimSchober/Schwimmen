package de.htwg.se.schwimmen

case class GameCard(wert: String, typ: String) {

  val wertList: List[String] = List("7", "8", "9", "10", "jack", "queen", "king", "ace")
  val typList: List[String] = List("heart", "diamond", "spade", "club")

  def isCard: Boolean = {
    for (e <- wertList) {
      if (wert == e) {
        return true
      }
    }
    false
  }

}
