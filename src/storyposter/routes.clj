(ns storyposter.routes
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [storyposter.stories.story :as s]
            [storyposter.users.user :refer [create-user-handler]]))

(defroutes all-routes
  (GET "/v1/stories" request (s/get-recent-stories))        ;GET recent stories
  (POST "/v1/story/" request)                               ;Create story with parts and heading
  (PUT "/v1/story/:story-id" request)                       ;Update whole story
  (PATCH "/v1/story/:story-id" request)                     ;Update a specific field of the user created story
  (PATCH "/v1/story/:story-id/part/:part-id" request)       ;Update a specific part's field of the user created story
  (DELETE "/v1/story/:story-id" request)                    ;DELETE Whole story
  (DELETE "/v1/story/:story-id/part/:part-id" request)      ;DELETE part of story
  (POST "/v1/user/login" request (create-user-handler (:body request))) ;Create User
  (GET "/v1/user/stories/" request)                         ;Get list of stories to continue reading for the user
  (PUT "/v1/user/story/:story-id/part/:part-id" request)   ; Mark part as read
  (route/not-found "Not Found"))


