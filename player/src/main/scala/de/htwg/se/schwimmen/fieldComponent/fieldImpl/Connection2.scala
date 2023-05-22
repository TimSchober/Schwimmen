package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import slick.jdbc.PostgresProfile.api._
object Connection2 {
  val db2 = Database.forConfig("postgres")
}
