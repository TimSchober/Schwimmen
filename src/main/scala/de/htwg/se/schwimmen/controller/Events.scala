package de.htwg.se.schwimmen.controller

import scala.swing.event.Event

  class NewGame extends Event
  case class PlayerAmountChanged(plAm: Int) extends Event
  class PlayerAdded extends Event
  class CardsChanged extends Event
  class KnockedChanged extends Event
  class PlayerChanged extends Event
  class YesSelected extends Event
  class CardSelected extends Event
