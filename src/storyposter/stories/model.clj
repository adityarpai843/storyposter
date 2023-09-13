(ns storyposter.stories.model
  (:require [methodical.core :as m]
            [toucan2.core :as t2]))
(m/defmethod t2/table-name :models/stories
             [_model]
             :stories)

