package de.htwg.se.schwimmen.cardStackComponent

import play.api.libs.json.JsObject

trait CardStackInterface {
  def setCardStack(): CardStackInterface
  def rndCardStack: List[(String, String)]
  def getThreeCards: List[(String, String)]
  def delThreeCards: CardStackInterface
  def getThreeCardsInJsonFormat: JsObject
}
