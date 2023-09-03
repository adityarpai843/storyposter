(ns storyposter.users.db
  (:require [buddy.core.codecs :refer :all]
            [storyposter.config :refer [db-spec]]
            [buddy.core.hash :as hash]
            [toucan2.core :as t2])
  (:import (java.text SimpleDateFormat)))

(defn create-hashed-password
  "Generate SHA3 hash for the users password"
  [password]
  (-> (hash/sha256 password)
      (bytes->hex)))

(defn create-user
  "Create a user data and save it to DB"
  [user]
  (let [datetime-format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        current-date-time (->> (new java.util.Date)
                               (.format datetime-format)
                               (java.sql.Timestamp/valueOf))
        db-data (-> user
                    (assoc :api-key (.toString (java.util.UUID/randomUUID)))
                    (assoc :created-at current-date-time)
                    (update :password create-hashed-password))]
    (t2/insert! :conn db-spec "users" db-data)
    (:api-key db-data)))