(ns core
  (:require [stasis.core :as stasis]
            [ring.adapter.jetty :as jetty]
            [hiccup2.core :as h]))

(def canonical [:body [:h1 "betakuwe"] [:h2 "profile"]])

(def pages {"/index.html" (str (h/html canonical))})

(defn get-pages [] pages)

(def app (stasis/serve-pages get-pages))

(defn -main [& _args]
  (jetty/run-jetty app {:port 3000}))

(def target-dir "out")

(defn export [& _args]
  (stasis/empty-directory! target-dir)
  (stasis/export-pages pages target-dir))