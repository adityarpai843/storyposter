(ns storyposter.user-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [storyposter.users.user :refer :all]))

(deftest create-user-test
  (let [mock-request (mock/request :post "http://localhost:3000/v1/user/login")]
    (testing "create a user with Username & Password"
      (let [request-with-body (-> mock-request
                                  (assoc :body {:username "adityaunit" :password "12345678"}))
            response (create-user-handler request-with-body)]
        (is (= response
               {:status  201
                :body    {:api-key (get-in response [:body :api-key])}}))))
    (testing "Create a user without Password"
      (let [request-with-body (-> mock-request
                                  (assoc :body {:username "adityaunit"}))
            response (create-user-handler request-with-body)]
        (is (= response
               {:status  400
                :body    {:error "Field(:password)(missing-required-key)"}}))))
    (testing "Create a user without Username"
      (let [request-with-body (-> mock-request
                                  (assoc :body {:password "12345678"}))
            response (create-user-handler request-with-body)]
        (is (= response
               {:status  400
                :body    {:error "Field(:username)(missing-required-key)"}}))))
    (testing "Create a user without Username or Password"
      (let [request-with-body (-> mock-request
                                  (assoc :body {}))
            response (create-user-handler request-with-body)]
        (is (= response
               {:status  400
                :body    {:error "Field(:username :password)(missing-required-key missing-required-key)"}}))))))

