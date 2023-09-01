(ns storyposter.routes
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [storyposter.users.user :refer [create-user-handler ]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (POST "/users" request
    (-> )
    (create-user-handler (:body request)))
  (route/not-found "Not Found"))
