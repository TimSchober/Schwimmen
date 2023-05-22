create extension hstore;
create schema cards;
create schema players;

create table if not exists cards."Card" ("card_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_value" VARCHAR NOT NULL,"card_color" VARCHAR NOT NULL);
create table if not exists players."Player" ("player_id" BIGSERIAL NOT NULL PRIMARY KEY, "player_name" VARCHAR NOT NULL,"life" INTEGER NOT NULL, "has_knocked" VARCHAR Not NULL, "first_card_value" VARCHAR Not NULL, "first_card_color" VARCHAR Not NULL, "second_card_value" VARCHAR Not NULL, "second_card_color" VARCHAR Not NULL, "third_card_value" VARCHAR Not NULL, "third_card_color" VARCHAR Not NULL);
create table if not exists players."PlayingField" ("playing_field_id" BIGSERIAL NOT NULL PRIMARY KEY, "field_first_card_value" VARCHAR Not NULL, "field_first_card_color" VARCHAR Not NULL, "field_second_card_value" VARCHAR Not NULL, "field_second_card_color" VARCHAR Not NULL, "field_third_card_value" VARCHAR Not NULL, "field_third_card_color" VARCHAR Not NULL);
create table if not exists cards."Actor" ("actor_id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL);
create table if not exists cards."CardActorMapping" ("card_actor_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"actor_id" BIGINT NOT NULL);
create table if not exists cards."StreamingProviderMapping" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"streaming_provider" VARCHAR NOT NULL);
create table if not exists cards."CardLocations" ("card_location_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"locations" text [] NOT NULL);
create table if not exists cards."CardProperties" ("id" bigserial NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"properties" hstore NOT NULL);
create table if not exists cards."ActorDetails" ("id" bigserial NOT NULL PRIMARY KEY,"actor_id" BIGINT NOT NULL,"personal_info" jsonb NOT NULL);