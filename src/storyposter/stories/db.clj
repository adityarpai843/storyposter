(ns storyposter.stories.db
  (:require [toucan2.core :as t2]
            [storyposter.config :refer [db-spec]]))

(defn get-recent-stories-from-db
  "Gets recent stories from the DB"
  []
  (t2/select :conn db-spec "stories" {:select [:stories.title]
                                      :from   [:stories]
                                      :order-by [:created-at]}))