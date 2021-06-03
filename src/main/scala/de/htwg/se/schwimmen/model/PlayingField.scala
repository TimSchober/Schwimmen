package de.htwg.se.schwimmen.model

case class PlayingField(stack: CardStack) extends AbstractPlayingField(stack: CardStack) {

  override def processPlayerAmount(amount: Int): Boolean = {
    amount match {
      case 2|3|4|5|6|7|8|9 => true
      case _ => false
    }
  }
}
