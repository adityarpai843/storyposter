(ns storyposter.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [storyposter.stories.story :as s]
            [storyposter.utils.middleware :as middleware]
            [storyposter.users.user :as u]))

(def all-routes
  (routes
    (GET "/v1/stories" request (s/get-recent-stories))
    (GET "/" [] "Hello World")
    (POST "/v1/user/login" request (u/create-user-handler request))
    (-> (context "/v1/story" []
          (POST "/" request (s/create-story-handler request))
          (PATCH "/:story-id" request (s/update-story-fields-handler request))
          (PUT "/part/:part-id" request (s/update-parts-handler request))
          (DELETE "/:story-id" request (s/delete-story-by-id-handler request))
          (DELETE "/:story-id/part/:part-id" request (s/delete-part request)))
        (wrap-routes middleware/user-authenticated))
    (-> (context "/v1/user" []
          (PATCH "/stories/:story-id" request (u/mark-story-read request))
          (GET "/story/:story-id" request (u/get-story-handler request))
          (GET "/stories" request (u/get-stories-to-continue request))
          (PATCH "/story/part/:part-id" request (u/mark-part-as-read request)))
        (wrap-routes middleware/user-authenticated))
    (route/not-found "No route found!!")))