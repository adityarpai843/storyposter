(ns storyposter.users.db
  (:require [buddy.core.codecs :refer :all]
            [storyposter.config :refer [db-spec]]
            [buddy.core.hash :as hash]
            [storyposter.utils.db :as db]
            [storyposter.utils.utils :refer [get-current-timestamp]]
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
                    (assoc :created-at (get-current-timestamp))
                    (update :password create-hashed-password))]
    (t2/insert! :conn db-spec "users" db-data)
    (:api-key db-data)))

(defn mark-story
  "Mark story as read or unread"
  [{:keys [read]} story-id user-details]
  (let [story (db/get-user-story-by-id story-id)
        user-id (:id user-details)
        updated-story (-> story
                          (assoc :id (.toString (java.util.UUID/randomUUID)))
                          (assoc :read read)
                          (assoc :uid user-id))]
    (t2/insert! :conn db-spec "stories" updated-story)))