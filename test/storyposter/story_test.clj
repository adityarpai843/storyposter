(ns storyposter.story-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
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

(deftest create-story-test
  (let [mock-request (mock/request :post "http://localhost:3000/v1/story")]
    (testing "Update with both fields"
      (let [response (create-story-handler (-> mock-request
                                               (assoc :user-data {:id         3
                                                                  :username   "Tim"})
                                               (assoc :body {:title "Mock Story",
                                                             :read false,
                                                             :parts [{:read false,
                                                                       :body "MockStory Mockstory MockStory MockstoryMockStory MockstoryMockStory Mockstory"},
                                                                      {:read false,
                                                                       :body "MockStory MockstoryMockStory MockstoryMockStory MockstoryMockStory MockstoryMockStory MockstoryMockStory Mockstory"}]})))]

        (is (= response
               {:status 201
                :body (:body response)}))))))

(deftest story-field-update-test
  (let [mock-request (mock/request :patch "http://localhost:3000/v1/story/d054a229-ede1-4afd-be57-794d9db0b7a2")]
    (testing "Update with both fields"
      (let [response (update-story-fields-handler (-> mock-request
                                                      (assoc :user-data {:id         3
                                                                         :username   "Tim"})
                                                      (assoc :body {:title "Mock aory"})))]

        (is (= response
               {:status 200
                :body "Story created"}))))
    (testing "Try to update using a another user who is not owner of story"
      (let [response (update-story-fields-handler (-> mock-request
                                                      (assoc :user-data {:id         4
                                                                         :username   "Jimmy"})
                                                      (assoc :body {:title "Mock ary"})))]

        (is (= response
               {:status 403
                :body {:error "Operation Forbidden"}}))))

    (testing "Trying to update a story that is not in the db"
      (let [response (update-story-fields-handler (-> mock-request
                                                      (assoc :user-data {:id         3
                                                                         :username   "Tim"})
                                                      (assoc :body {:title "Mock ary"})))]

        (is (= response
               {:status 404
                :body "story not found"}))))))
