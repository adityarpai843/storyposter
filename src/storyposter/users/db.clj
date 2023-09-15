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

(defn get-story-to-read
  "Gets story from DB for the user to start reading"
  [story user-id]
  (let [updated-story (-> story
                          (assoc :id (.toString (java.util.UUID/randomUUID)))
                          (assoc :uid user-id))
        parts (t2/select :conn db-spec :parts :story-id (:id story))
        updated-parts (map (fn [part]
                             (-> part
                                 (assoc :id (.toString (java.util.UUID/randomUUID)))
                                 (assoc :story_id (:id updated-story)))
                             )parts)]
    (t2/insert! :conn db-spec :parts updated-parts)
    (t2/insert! :conn db-spec :stories updated-story)
    (t2/select :conn db-spec :stories {:select   [:stories.id
                                                  :stories.title
                                                  :parts.body]
                                       :from     [:stories]
                                       :left-join [:parts [:= :parts.story-id :stories.id]]
                                       :where    [:and [:= :stories.id (:id updated-story)] [:= :stories.uid user-id]]})))