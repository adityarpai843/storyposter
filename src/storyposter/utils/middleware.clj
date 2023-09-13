(ns storyposter.utils.middleware
    (:require [storyposter.utils.status :refer [unauthorized]]
              [storyposter.utils.db :as db]))

(defn get-user-data-handler
  "Check whether user exists or not"
  [request api_key]
  (let [db-data  (db/get-user-data api_key)]
    (if (empty? db-data)
      (unauthorized)
      (assoc request :user-data db-data))))

(defn user-authenticated
  "auth"
  [handler]
  (fn [request]
    (let [headers (:headers request)
          api-key (get headers "x-api-key")]
      (handler (get-user-data-handler request api-key)))))