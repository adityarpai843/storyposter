(ns storyposter.users.user
  (:require [storyposter.users.db :as db]
            [schema.core :as s]
            [storyposter.utils.status :refer [created bad-request success]]
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
