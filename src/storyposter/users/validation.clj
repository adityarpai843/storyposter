(ns storyposter.users.validation
  (:require [schema.core :as s
             :include-macros true]))

(s/defschema UserSchema
  "Schema for user request data"
  {:username s/Str :password s/Str})