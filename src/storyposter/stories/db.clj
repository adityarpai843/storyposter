(ns storyposter.stories.db
  (:require [toucan2.core :as t2]
            [storyposter.utils.utils :refer [get-current-timestamp]]
            [storyposter.config :refer [db-spec]]))

(defn get-recent-stories-from-db
  "Gets recent stories from the DB"
  []
  (t2/select :conn db-spec "stories" {:select   [:stories.id
                                                 :stories.title
                                                 :parts.body]
                                      :from     [:stories]
                                      :join     [:parts [:= :stories.id :parts.story_id]]
                                                 :order-by [:stories.created-at]}))

(defn user-parts-db-data
  "Creates user story parts"
  [{:keys [id]} body timestamp]
  (let [parts (:parts body)
        parts-db-data (map (fn [part]
                             (-> part
                                 (assoc :id (.toString (java.util.UUID/randomUUID)))
                                 (assoc :story-id id)
                                 (assoc :created-at timestamp)))
                           parts)]
    parts-db-data))

(defn create-user-stories
  "Create user stories"
  [body user-details]
  (let [timestamp        (get-current-timestamp)
        story-db-data    (-> body
                             (dissoc :parts)
                             (assoc :id (.toString (java.util.UUID/randomUUID)))
                             (assoc :created-by (:id user-details))
                             (assoc :created-at timestamp))
        parts-db-data    (user-parts-db-data story-db-data body timestamp)]
    (t2/insert! :conn db-spec "parts" parts-db-data)
    (t2/insert! :conn db-spec "stories" story-db-data)
    story-db-data))