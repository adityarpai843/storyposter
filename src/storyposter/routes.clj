(ns storyposter.routes
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [storyposter.users.user :refer [create-user-handler]]))

(defroutes user-routes
  (POST "/v1/login" request (create-user-handler (:body request)))
  (route/not-found "Not Found"))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))
