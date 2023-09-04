(ns storyposter.utils.db
  (:require [toucan2.core :as t2]
            [storyposter.config :refer [db-spec]]))

(defn get-user-id
  "Given API key get the user's ID"
  [api_key]
  (let [user-id (t2/select :conn db-spec "users" {:select [:users.id]
                                                  :from   [:users]
                                                  :where  [= :api-key api_key]})]
    user-id))

(defn check-user-existence?
  "Check whether user exists or not"
  [api_key]
  (let [user-id (get-user-id api_key)]
    (empty? user-id)))
