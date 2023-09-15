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

(defn update-story
  "Update a specific story created by that user"
  [part-id body]
  (db/update-story part-id body))

(defn update-parts-handler
  "Handler for update story"
  [request]
  (let [part-id (get-in request [:params :part-id])
        user-details (:user-data request)
        body (:body request)
        story (udb/get-user-story-by-id (:story-id body))
        validate-schema (s/check v/UpdatePartsSchema body)]
    (if (t2/exists? :conn db-spec :parts :id part-id)
      (if (not validate-schema)
        (if (= (:id user-details) (:created_by story))
          (do
            (update-story part-id body)
            (success "Part updated successfully"))
          (forbidden {:error "Operation Forbidden"}))
        (bad-request {:error (str "Field" (keys validate-schema) (vals validate-schema))}))
      (not-found "Part not found"))))

(defn delete-part
  "Delete a specific part in a story"
  [request]
  (let [part-id (get-in request [:params :part-id])]
    (if (t2/exists? :conn db-spec :parts :id part-id)
      (do
        (db/delete-part part-id)
        (accepted "Part Deleted successfully"))
      (not-found "Part not found"))))
