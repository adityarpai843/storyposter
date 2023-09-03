(ns storyposter.utils.status)

(defn success
  "Denotes 200 status"
  [body]
  {:status 200
   :body   body})

(defn created
  "Denotes 201 status"
  [body]
  {:status 201
   :body   body})

(defn bad-request
  "Denotes 400 status"
  [body]
  {:status 400
   :body   body})

(defn no-content
  "Denotes 204 status"
  [body]
  {:status 204
   :body   body})
