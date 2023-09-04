(ns storyposter.story-test
  (:require [clojure.test :refer :all]
            [storyposter.stories.story :refer :all]))

(deftest get-recent-stories-test
  (testing "Get Recent Stories"
    (let [response (get-recent-stories)]
      (is (or (= response
                 {:status 200
                  :body   {:error "No stories created"}})
              (= response
                 {:status 200
                  :body   response}))))))
