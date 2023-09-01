(ns storyposter.users.user
  (:require [storyposter.users.db :as db]
            [schema.core :as s]
            [storyposter.users.validation :refer [UserSchema]]))

(defn send-success-response
  "Sends the API Key with status code"
  [key]
  {:status  201
   :body    {:api-key key}})

(defn create-user-handler
  "Handler for creating a user and returning API key"
  [request-body]
  (let [validate-schema (s/check UserSchema request-body)]
    (if (not validate-schema)
      (->> request-body
           db/create-user
           send-success-response)
      {:status  400
       :body    {:error (str "Field" (keys validate-schema) (vals validate-schema))}})))

