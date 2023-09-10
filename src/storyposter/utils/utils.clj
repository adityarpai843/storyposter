(ns storyposter.utils.utils
  (:import (java.text SimpleDateFormat)))

(defn get-current-timestamp
  "Gets current timestamp for DB"
  []
  (let [datetime-format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        current-timestamp (->> (new java.util.Date)
                               (.format datetime-format)
                               (java.sql.Timestamp/valueOf))]
    current-timestamp))
