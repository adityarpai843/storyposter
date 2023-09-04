(ns storyposter.routes
  (:require [compojure.core :refer :all]
            [storyposter.stories.story :as s]
            [storyposter.utils.middleware :as middleware]
            [storyposter.users.user :refer [create-user-handler]]))

(def all-routes
  (routes
    (GET "/v1/stories" request (s/get-recent-stories))
    (GET "/" [] "Hello World")
    (POST "/v1/user/login" request (create-user-handler request))
    (-> (context "/v1/story" []
          (POST "/" request)
          (PATCH "/:story-id" request)
          (PATCH "/:story-id/part/:part-id" request)
          (DELETE "/:story-id" request)
          (DELETE "/:story-id/part/:part-id" request))
        (wrap-routes middleware/user-authenticated))
    (-> (context "/v1/user" []
          (GET "/stories" request)
          (PUT "/story/:story-id/part/:part-id" request))
        (wrap-routes middleware/user-authenticated))))