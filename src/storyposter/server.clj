(ns storyposter.server
  (:require [ring.middleware.json :as middleware]
            [storyposter.routes :refer [app-routes user-routes]]))

(def app
  (-> user-routes
      (middleware/wrap-json-body {:key-fn keyword})
      (middleware/wrap-json-response)))
