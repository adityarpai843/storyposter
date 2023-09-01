(ns storyposter.server
  (:require [ring.middleware.json :as middleware]
            [storyposter.routes :refer [app-routes]]))

(def app
  (-> app-routes
      (middleware/wrap-json-body {:key-fn keyword})
      (middleware/wrap-json-response)))
