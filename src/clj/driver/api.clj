(ns driver.api
  (:require [cheshire.core :as json]
            [clojure.tools.logging :refer [info]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.coercions :refer [as-int]]
            [compojure.route :as route]
            [driver.store :as store]
            [environ.core :refer [env]]
            [mount.core :refer [defstate]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes routes
  (GET "/stats" [] (json/generate-string (store/stats)))
  (POST "/set-target-rate" [rate :<< as-int org-id :<< as-int]
    (store/set-target-rate rate org-id)
    {:status 204})
  (POST "/pulse" [duration :<< as-int rate :<< as-int]
    (store/pulse duration rate)
    {:status 204})
  (POST "/reset" []
    (store/reset)
    {:status 204})
  (route/not-found (json/generate-string {:error "Not Found"})))

(def app (wrap-params routes))

(def ^:private http-server-enabled? (atom false))

(defn enable-http-server []
  (reset! http-server-enabled? true))

(defstate ^:private server
  :start
  (when @http-server-enabled?
    (info "Starting kleiner-driver api" {:port 8080})
    (run-server app {:port 8080}))
  :stop
  (when @http-server-enabled?
    (server :timeout 100)))
