(ns storyposter.users.user
  (:require [storyposter.users.db :as db]
            [storyposter.utils.db :as udb]
            [toucan2.core :as t2]
            [storyposter.config :refer [db-spec]]
            [schema.core :as s]
            [storyposter.utils.status :refer [created bad-request success not-found]]
            [storyposter.users.validation :refer [UserSchema StoryUpdate]]))
(defn create-user-handler
  "Handler for creating a user and returning API key"
  [{:keys [body]}]
  (let [validate-schema (s/check UserSchema body)]
    (if (not validate-schema)
      (created {:api-key (->> body
                              db/create-user)})
      (bad-request {:error (str "Field" (keys validate-schema) (vals validate-schema))}))))

(defn mark-story-read
  "Handler for marking story read"
  [request]
  (let [body (:body request)
        user-details (:user-data request)
        story-id (get-in request [:params :story-id])
        validate-schema (s/check StoryUpdate body)]
    (if (not validate-schema)
      (do
        (db/mark-story body story-id user-details)
        (success "marked as read"))
      (bad-request {:error (str "Field" (keys validate-schema) (vals validate-schema))}))))

(defn get-story
  "Gets a story for the user to read"
  [story-id user-details]
  (let [story (udb/get-user-story-by-id story-id)]
    (db/get-story-to-read story (:id user-details))))

(defn get-story-handler
  "Handler for get story endpoint"
  [request]
  (let [body  (:body request)
        story-id (get-in request [:params :story-id])
        user-details (:user-data request)]
    (if (t2/exists? :conn db-spec :stories :id story-id)
      (success (get-story story-id user-details))
      (not-found "Story not found"))))
