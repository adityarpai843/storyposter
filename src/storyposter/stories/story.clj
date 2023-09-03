(ns storyposter.stories.story
  (:require [storyposter.stories.db :as db]
            [storyposter.utils.status :refer [success]]))

(defn get-recent-stories
  "Get recent stories or return no stories"
  []
  (let [recent-stories (db/get-recent-stories-from-db)]
    (if (empty? recent-stories)
      (success {:error "No stories created"})
      (success recent-stories))))

