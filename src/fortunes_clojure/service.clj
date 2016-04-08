(ns fortunes-clojure.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [clojure.data.json :as json]
            [ring.util.response :as ring-resp]
            [fortunes-clojure.cookies :as cookies]))

(defn home-action
  [request]
  (ring-resp/response (json/write-str {:message "Fortune API", :status "ok"})))

(defn fortunes-action
  [request]
  (ring-resp/response (json/write-str (cookies/get-random-fortune))))

(defroutes routes
  [[["/" {:get home-action}
     ^:interceptors [(body-params/body-params) bootstrap/json-body]
     ["/fortunes" {:get fortunes-action}]]]])

(def service {:env :prod
              ::bootstrap/routes routes

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ::bootstrap/type :jetty
              ;;::bootstrap/host "localhost"
              ::bootstrap/port 8080})
