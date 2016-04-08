;; FaaS - Fortunes as a Service
(ns fortunes-clojure.cookies)
(use '[clojure.string :only (split trim)])

(def ^:private fortunes-path "data/fortunes")
(def ^:private split-fortunes-regex #"%")
(def ^:private split-fortune-regex #"\s+―\s+")

(defn- clean-up [text] (trim (clojure.string/replace text #"\s+" " ")))

(defn- extract-fortune
  [fortune]
  (into {}
        (map vector [:quote, :author],
             (map clean-up (split fortune split-fortune-regex)))))

(def fortunes
  (map extract-fortune (split (slurp fortunes-path) split-fortunes-regex)))

(defn get-random-fortune [] (rand-nth fortunes))
