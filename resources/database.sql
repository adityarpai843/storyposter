CREATE TABLE "parts" (
  "id" varchar PRIMARY KEY,
  "story_id" varchar NOT NULL,
  "read" boolean,
  "header" varchar,
  "body" varchar NOT NULL,
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
  "id" varchar PRIMARY KEY,
  "title" varchar NOT NULL,
  "read" boolean,
  "uid" integer,
  "created_by" integer NOT NULL,
  "created_at" timestamp NOT NULL
);

ALTER TABLE "parts" ADD FOREIGN KEY ("story_id") REFERENCES "stories" ("id");

ALTER TABLE "stories" ADD FOREIGN KEY ("uid") REFERENCES "users" ("id");
