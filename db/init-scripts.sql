create extension hstore;
create schema cards;
create table if not exists cards."Card" ("card_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_value" VARCHAR NOT NULL,"card_color" VARCHAR NOT NULL);
create table if not exists cards."Actor" ("actor_id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL);
create table if not exists cards."CardActorMapping" ("card_actor_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"actor_id" BIGINT NOT NULL);
create table if not exists cards."StreamingProviderMapping" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"streaming_provider" VARCHAR NOT NULL);
create table if not exists cards."CardLocations" ("card_location_id" BIGSERIAL NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"locations" text [] NOT NULL);
create table if not exists cards."CardProperties" ("id" bigserial NOT NULL PRIMARY KEY,"card_id" BIGINT NOT NULL,"properties" hstore NOT NULL);
create table if not exists cards."ActorDetails" ("id" bigserial NOT NULL PRIMARY KEY,"actor_id" BIGINT NOT NULL,"personal_info" jsonb NOT NULL);