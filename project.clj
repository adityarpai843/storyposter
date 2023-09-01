(defproject storyposter "0.1.0-SNAPSHOT"
  :description "Story Poster App"
  :url "http://github.com/adityarpai843/storyposter"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [io.github.camsaul/toucan2 "1.0.524"]
                 [prismatic/schema "1.4.1"]
                 [buddy/buddy-core "1.8.0"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-defaults "0.3.4"]
                 [org.postgresql/postgresql "42.2.10"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler storyposter.server/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
