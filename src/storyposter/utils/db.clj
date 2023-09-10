(ns storyposter.utils.db
  (:require [toucan2.core :as t2]
            [toucan2.tools.after-select :refer :all]
            [honey.sql.helpers :refer [select from where]]
            [storyposter.config :refer [db-spec]]))
(defn get-user-data
  "Given API key get the user's ID"
  [api_key]
  (let [user-details (t2/select-one :conn db-spec "users" (-> (select [:*])
                                                              (from :users)
                                                              (where [:= :api-key api_key])))]
    user-details))

;(t2/select-one :conn db-spec :models/users :api-key api_key)


#_(t2/select :conn db-spec "users" (-> (select [:users.id])
                                     (from :users)
                                     (where [:= :api-key api_key])))
