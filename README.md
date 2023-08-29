# Story Poster
A REST compliant API service, which will be used for a story posting site. A user can read stories posted by other users. Authenticate with API key, no UI required. A story can have multiple parts.
 
APIs supports:

1. Get recent stories to read.
2. Get list of stories to continue reading for a user.
3. Mark a part of story as read.
4. Create a new story. Add parts to the story. Entire CRUD.
5. Story BLOB can be simple text, no need to handle image uploads or rich text.
6. Creating a new user - returns with an API key to use.
7. Should handle possible error cases (example: if story has 5 parts, but API call requested 7th part - thats an error) and return correct HTTP codes.

Infrastructure:

1. Backend service
2. Postgres database

Libraries to use:

1. Compojure
2. Plumatic/schema
3. Toucan 2 + HoneySQL

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server
