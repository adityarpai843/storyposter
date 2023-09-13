(ns storyposter.utils.middleware
    (:require [storyposter.utils.status :refer [unauthorized]]
              [storyposter.utils.db :as db]))

;TODO Use query for user fetch and not exist cases
(defn get-user-data-handler
  "Check whether user exists or not"
  [request api_key]
  (let [user-details (array-map :id         3
                                :username   "Tim")
        db-data  (db/get-user-data api_key)]
    (if (empty? user-details)
      (unauthorized)
      (assoc request :user-data user-details))))

(defn user-authenticated
  "auth"
  [handler]
  (fn [request]
    (let [headers (:headers request)
          api-key (get headers "x-api-key")]
      (handler (get-user-data-handler request api-key)))))