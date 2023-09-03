(ns storyposter.server
  (:require [ring.middleware.json :as middleware]
            [storyposter.routes :refer [all-routes]]))

(def app
  (-> all-routes
      (middleware/wrap-json-body {:key-fn keyword})
      (middleware/wrap-json-response)))
