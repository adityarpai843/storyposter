(ns storyposter.utils.middleware
    (:require [storyposter.utils.db :as db]
              [ring.util.response :as r]
              [storyposter.utils.status :refer [unauthorized]]))

(defn user-authenticated
  "Middleware that checks whether user is authenticated"
  [handler]
  (fn [request]
    (let [api-key      (get-in request [:headers :x-api-key])
          user-exists? (db/check-user-existence? "1234578")]
      (println "api-key" api-key)
      (if user-exists?
        (handler request)
        (unauthorized)))))
