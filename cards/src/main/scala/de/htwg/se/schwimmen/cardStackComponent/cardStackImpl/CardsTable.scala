package de.htwg.se.schwimmen.cardStackComponent.cardStackImpl

case class Card(id: Long, cardValue: String, cardColor: String)
object SlickTables {
  import slick.jdbc.PostgresProfile.api._

  class CardsTable(tag: Tag) extends Table[(Long, String, String)](tag, Some("cards"), "Card") {

    def id = column[Long]("card_id", O.PrimaryKey, O.AutoInc)
    def cardValue = column[String]("card_value")
    def cardColor = column[String]("card_color")
    override def * = (id, cardValue, cardColor) 
  }
  lazy val cardsTable = TableQuery(CardsTable(_))

}
