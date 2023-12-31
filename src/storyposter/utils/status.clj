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

(defn not-found
  "Denotes 404 status"
  [body]
  {:status 404
   :body   body})

(defn forbidden
  "Forbidden status"
  [body]
  {:status 403
   :body   body})

(defn unauthorized
  "Denotes 401 unauthorized"
  []
  {:status 401
   :body {:error "User Unauthorized"}})

(defn accepted
  "Denotes a 202 status"
  [body]
  {:status 202
   :body body})
