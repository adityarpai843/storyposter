(ns storyposter.users.user
  (:require [storyposter.users.db :as db]
            [schema.core :as s]
            [storyposter.users.validation :refer [UserSchema]]))

(defn send-success-response
  "Sends the API Key with status code"
  [key]
  {:status  201
   :headers {"Location" (str '\' 'users')}
   :body    {:api-key key}})

(defn validate-request-data
  "Check whether request data is valid or not"
  [request]
  (when-not (s/validate UserSchema (:body request))
    {:status  400
     :headers {"Location" (str '\' 'users')}
     :body    {:error "Data Error"}}))


(defn create-user-handler
  "Handler for creating a user and returning API key"
  [request-body]
  (->> request-body
       db/create-user
       send-success-response))

