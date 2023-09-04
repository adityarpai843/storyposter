CREATE TYPE status as ENUM ('notstarted','inprogress','done');

CREATE TABLE "parts" (
  "id" serial PRIMARY KEY,
  "story_id" integer,
  "current_status" status,
  "header" varchar,
  "body" varchar,
  "created_at" timestamp
);

CREATE TABLE "users" (
  "id" serial PRIMARY KEY,
  "username" varchar NOT NULL,
  "password" varchar NOT NULL,
  "api_key" varchar NOT NULL,
  "created_at" timestamp
);

CREATE TABLE "stories" (
  "id" serial PRIMARY KEY,
  "title" varchar NOT NULL,
  "status" status,
  "user_id" integer,
  "created_by" integer NOT NULL,
  "created_at" timestamp NOT NULL
);

ALTER TABLE "parts" ADD FOREIGN KEY ("story_id") REFERENCES "stories" ("id");

ALTER TABLE "stories" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
