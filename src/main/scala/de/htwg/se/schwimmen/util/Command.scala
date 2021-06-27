package de.htwg.se.schwimmen.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}