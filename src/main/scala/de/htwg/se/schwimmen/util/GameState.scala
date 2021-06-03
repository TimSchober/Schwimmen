package de.htwg.se.schwimmen.util

trait GameState

case class nextPlayer() extends GameState {
  def nextPlayer: String = State.state match {
    case "Player1" => "Player2"
    case "Player2" => "Player1"
  }
}
case class gameOver() extends GameState {
  def gameOver: String = State.state match {
    case "Player1" => "Player1 has won"
    case "Player2" => "Player2 has won"
  }
}
