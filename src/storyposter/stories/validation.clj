(ns storyposter.stories.validation
  (:require [schema.core :as s
             :include-macros true]))

(s/defschema PartsSchema
  "Schema for user story Parts data"
  {(s/required-key :read)   s/Bool
   (s/optional-key :header) s/Str
   (s/required-key :body)   s/Str})

(s/defschema StoriesSchema
  "Schema for Story request data"
  {(s/required-key :title)  s/Str
   (s/required-key :read)   s/Bool
   (s/required-key :parts)  [PartsSchema]})

(s/defschema UpdatePartsSchema
  "Schema for user story Parts data"
  {(s/optional-key :header)    s/Str
   (s/required-key :story-id)  s/Str
   (s/required-key :body)      s/Str})
