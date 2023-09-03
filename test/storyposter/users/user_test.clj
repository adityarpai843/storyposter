(ns storyposter.users.user-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [storyposter.users.user :refer :all]))

(deftest your-json-handler-test
  (let [mock-request (-> (mock/request :post "http://localhost:3000/v1/user/login")
                         (mock/content-type "application/json")
                         (mock/json-body {:username "adityaunit" :password "12345678"}))]
    (is (= (create-user-handler (:body mock-request))
           {:status  201
            :body    {:api-key uuid?}}))))