(ns storyposter.users.user
  (:require [storyposter.users.db :as db]
            [schema.core :as s]
            [storyposter.utils.status :refer [created bad-request]]
            [storyposter.users.validation :refer [UserSchema]]))
(defn create-user-handler
  "Handler for creating a user and returning API key"
  [request-body]
  (let [validate-schema (s/check UserSchema request-body)]
    (if (not validate-schema)
      (created {:api-key (->> request-body
                              db/create-user)})
      (bad-request {:error (str "Field" (keys validate-schema) (vals validate-schema))}))))

