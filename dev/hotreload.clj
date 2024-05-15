(ns hotreload
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [core :refer [app]])
  (:gen-class))

(def dev-handler
  (wrap-reload app))

(defn -main
  "Runs a dev session with hot reloading on http://localhost:13000."
  [& _args]
  (run-jetty dev-handler {:port 13000}))
