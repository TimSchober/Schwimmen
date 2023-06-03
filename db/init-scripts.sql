create extension hstore;
create schema cards;
create schema players;

create table if not exists cards."Card" ("card_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_value" VARCHAR NOT NULL,"card_color" VARCHAR NOT NULL);
create table if not exists players."Player" ("player_id" BIGSERIAL NOT NULL PRIMARY KEY, "player_name" VARCHAR NOT NULL,"life" INTEGER NOT NULL, "has_knocked" VARCHAR Not NULL, "first_card_value" VARCHAR Not NULL, "first_card_color" VARCHAR Not NULL, "second_card_value" VARCHAR Not NULL, "second_card_color" VARCHAR Not NULL, "third_card_value" VARCHAR Not NULL, "third_card_color" VARCHAR Not NULL, "turn" VARCHAR Not NULL);
create table if not exists players."PlayingField" ("playing_field_id" BIGSERIAL NOT NULL PRIMARY KEY, "field_first_card_value" VARCHAR Not NULL, "field_first_card_color" VARCHAR Not NULL, "field_second_card_value" VARCHAR Not NULL, "field_second_card_color" VARCHAR Not NULL, "field_third_card_value" VARCHAR Not NULL, "field_third_card_color" VARCHAR Not NULL);
