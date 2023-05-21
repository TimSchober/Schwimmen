package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import slick.jdbc.PostgresProfile.api._
object Connection {

  val db = Database.forConfig("postgres")

}