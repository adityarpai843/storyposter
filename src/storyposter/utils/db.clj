(ns storyposter.utils.db
  (:require [toucan2.core :as t2]
            [toucan2.tools.after-select :refer :all]
            [storyposter.config :refer [db-spec]]))
(defn get-user-data
  "Given API key get the user's ID"
  [api_key]
  (let [user-details (t2/select-one :conn db-spec "users" :api-key api_key)]
    user-details))

(defn get-user-story-by-id
  "Get story by id"
  [story-id]
  (t2/select-one :conn db-spec "stories" :id story-id))
