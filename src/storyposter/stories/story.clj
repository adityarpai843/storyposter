(ns storyposter.stories.story
  (:require [schema.core :as s]
            [storyposter.stories.db :as db]
            [storyposter.stories.validation :as v]
            [storyposter.utils.status :refer [success created bad-request forbidden]]))

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

(defn update-fields
  "Update the DB column if user owns the story"
  [story-id user-details body]
  (let [story (db/get-user-story-by-id story-id)]
    (if (= (:created_by story) (:id user-details))
      (do
        (db/update-story-columns story-id body)
        (success "Story created"))
      (forbidden {:error "Operation Forbidden"}))))

(defn update-story-fields-handler
  "Update Fields in a story"
  [request]
  (let [body (:body request)
        story-id (get-in request [:params :story-id])
        user-details (:user-data request)]
    (if (or (contains? body :title) (contains? body :read))
      (update-fields story-id user-details body)
      (bad-request {:error "Only title or read can be updated"}))))

