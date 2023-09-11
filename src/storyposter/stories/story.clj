(ns storyposter.stories.story
  (:require [schema.core :as s]
            [storyposter.stories.db :as db]
            [storyposter.stories.validation :as v]
            [storyposter.utils.status :refer [success created bad-request]]))

(defn get-recent-stories
  "Get recent stories or return no stories"
  []
  (let [recent-stories (db/get-recent-stories-from-db)]
    (if (empty? recent-stories)
      (success {:error "No stories created"})
      (success recent-stories))))

(defn create-story-handler
  "Create Story"
  [request]
  (let [body (:body request)
        user-details (:user-data request)
        validate-schema (s/check v/StoriesSchema body)]
  (if (not validate-schema)
    (created (-> body
                 (db/create-user-stories user-details)))
    (bad-request {:error (str "Field" (keys validate-schema) (vals validate-schema))}))))

(defn update-story-fields-handler
  "Update Fields in a story"
  [request]
  (let [body (:body request)
        user-details (:user-data request)
        validate-schema (s/validate v/UpdateStorySchema body)]
    (println "story" (db/get-user-story-by-id "10983874-9bdc-4755-8e78-d37d1654b5d4"))))

