(ns core
  (:require [stasis.core :as stasis]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.adapter.jetty :as jetty]
            [hiccup.page :refer [html5]]
            [garden.core :refer [css]]
            [garden.units :refer [cm]]
            [garden.color :refer [rgb rgba]]
            [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]
            [talltale.core :refer [lorem-ipsum]]))

(def canonical
  [:head
   [:title "Benjamin Tan Kuan Wei"]
   [:meta {:name    "description"
           :content "Personal website of Benjamin Tan Kuan Wei"}]
   [:meta {:charset "utf-8"}]
   [:meta {:name    "viewport"
           :content "width=device-width, initial-scale=1.0"}]
   [:base {:href "/"}]
   [:link {:rel "stylesheet" :href "css/reset.css"}]
   [:style (css [[:body {:color      "blue"
                         :background (rgb 204 204 204)}]
                 [:#resume {:color         "black"
                            :background    "white"
                            :max-width     (cm 21)
                            :max-height    (cm 29.7)
                            :display       "block"
                            :margin        [0 "auto"]
                            :margin-bottom (cm 0.5)
                            :box-shadow    [0 0 (cm 0.5) (rgba 0 0 0 0.5)]
                            :margin-left   "auto"
                            :margin-right  "auto"}]])]
   [:body
    [:header "oh no"]
    [:img {:src "images/display_profile.png"}]
    [:h1 "betakuwe"]
    [:h2 "profile"]
    [:article#resume
     (apply str (repeatedly 10 lorem-ipsum))]]])

(def pages {"/index.html" (html5 {:lang "en"} canonical)})

(defn get-pages [] pages)

(defn get-assets []
  (assets/load-assets "public" ["/css/reset.css" #"/images/.*\.png$"]))

(def app
  (-> (stasis/serve-pages get-pages)
      (optimus/wrap get-assets optimizations/all serve-live-assets)
      wrap-content-type))

(defn -main
  "Serves a live server.
  I probably won't use it since this is just a static site."
  [& _args]
  (jetty/run-jetty app {:port 3000}))

(defn export
  "Builds and exports the website to `target-dir`.
  Configured in `deps.edn`."
  [{:keys [target-dir]}]
  (let [assets (optimizations/all (get-assets) {})]
    (stasis/empty-directory! target-dir)
    (optimus.export/save-assets assets target-dir)
    (stasis/export-pages pages target-dir)))