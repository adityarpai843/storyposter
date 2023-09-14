(ns storyposter.stories.story
  (:require [schema.core :as s]
            [storyposter.stories.db :as db]
            [storyposter.utils.db :as udb]
            [storyposter.config :refer [db-spec]]
            [toucan2.core :as t2]
            [storyposter.stories.validation :as v]
            [storyposter.utils.status :refer [success created bad-request forbidden not-found
                                              accepted]]))

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
  (let [story (udb/get-user-story-by-id story-id)]
    (if (= (:created_by story) (:id user-details))
      (do
        (db/update-story-columns story-id body)
        (success "title updated"))
      (forbidden {:error "Operation Forbidden"}))))

(defn update-story-fields-handler
  "Update Fields in a story"
  [request]
  (let [body (:body request)
        story-id (get-in request [:params :story-id])
        user-details (:user-data request)]
    (if (t2/exists? :conn db-spec :stories :id story-id)
      (if (contains? body :title)
        (update-fields story-id user-details body)
        (bad-request {:error "Only title can be updated"}))
      (not-found "story not found"))))

(defn delete-story-by-id-handler
  "Delete a specific story created by that user"
  [request]
  (let [story-id (get-in request [:params :story-id])
        user-details (:user-data request)]
    (if (t2/exists? :conn db-spec :stories :id story-id)
      (let [story (udb/get-user-story-by-id story-id)]
        (if (= (:created_by story) (:id user-details))
          (do
            (db/delete-story story-id)
            (accepted "Story Deleted Successfully"))
          (forbidden {:error "Operation Forbidden"})))
      (not-found "story not found"))))

