(ns storyposter.users.db
  (:require [buddy.core.codecs :refer :all]
            [storyposter.config :refer [db-spec]]
            [buddy.core.hash :as hash]
            [toucan2.core :as t2]))

(defn create-hashed-password
  "Generate SHA3 hash for the users password"
  [password]
  (-> (hash/sha256 password)
      (bytes->hex)))

(defn create-user
  "Create a user data and save it to DB"
  [user]
  (let [db-data (-> user
                    (assoc :api-key (.toString (java.util.UUID/randomUUID)))
                    (update :password create-hashed-password))]
    (t2/insert! :conn db-spec "users" db-data)
    (:api-key db-data)))