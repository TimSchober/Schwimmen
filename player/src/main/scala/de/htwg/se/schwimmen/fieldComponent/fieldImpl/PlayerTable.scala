package de.htwg.se.schwimmen.fieldComponent.fieldImpl

object SlickPlayerTable {
  import slick.jdbc.PostgresProfile.api._

  class PlayerTable(tag: Tag) extends Table[(
    Long, String, Int, String,
    String, String, String, 
    String, String, String)](tag, Some("players"), "Player") {

    def id = column[Long]("player_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("player_name")
    def life = column[Int]("life")
    def hasKnocked = column[String]("has_knocked")
    def firstCardValue = column[String]("first_card_value")
    def firstCardColor = column[String]("first_card_color")
    def secondCardValue = column[String]("second_card_value")
    def secondCardColor = column[String]("second_card_color")
    def thirdCardValue = column[String]("third_card_value")
    def thirdCardColor = column[String]("third_card_color")
    override def * = (id, name, life, hasKnocked, 
      firstCardValue, firstCardColor,
      secondCardValue, secondCardColor,
      thirdCardValue, thirdCardColor)
  }
  
  lazy val playersTable = TableQuery(PlayerTable(_))

}
