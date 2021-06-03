package de.htwg.se.schwimmen.util

object State {
  var state = "Player1"
  def handle(gameState: GameState): String = {
    gameState match {
      case a: nextPlayer => state = nextPlayer().nextPlayer
      case b: gameOver => state = gameOver().gameOver
    }
    state
  }
}
