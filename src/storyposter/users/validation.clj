(ns storyposter.users.validation
  (:require [schema.core :as s
             :include-macros true]))

(s/defschema UserSchema
  "Schema for user request data"
  {(s/required-key :username) s/Str  (s/required-key :password) s/Str})