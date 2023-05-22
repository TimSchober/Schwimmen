package de.htwg.se.schwimmen.fieldComponent.fieldImpl

object SlickPlayingTable {

  import slick.jdbc.PostgresProfile.api._
  class PlayingFieldTable(tag: Tag) extends Table[(
    Long, String, String, String,
      String, String, String)](tag, Some("players"), "PlayingField") {

    def id = column[Long]("playing_field_id")

    def firstCardValue = column[String]("field_first_card_value")

    def firstCardColor = column[String]("field_first_card_color")

    def secondCardValue = column[String]("field_second_card_value")

    def secondCardColor = column[String]("field_second_card_color")

    def thirdCardValue = column[String]("field_third_card_value")

    def thirdCardColor = column[String]("field_third_card_color")

    override def * = (id, firstCardValue, firstCardColor, secondCardValue, secondCardColor, thirdCardValue, thirdCardColor)
  }
  lazy val playingFieldTable = TableQuery(PlayingFieldTable(_))
}
