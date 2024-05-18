(ns core
  (:require [stasis.core :as stasis]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.adapter.jetty :as jetty]
            [hiccup.page :refer [html5]]
            [hiccup2.core :refer [html]]
            [hiccup.util :refer [raw-string]]
            [garden.core :refer [css]]
            [garden.units :refer [cm vw in px mm em]]
            [garden.color :refer [rgb rgba]]
            [garden.stylesheet :refer [at-media]]
            [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]
            [talltale.core :refer [lorem-ipsum]]))

;; https://coolors.co/393d3f-fdfdff-c6c5b9-595934
(def onyx "#393D3F")
(def snow "#F6F1F4")
(def serenity "#B1C9E8")
(def dark-blue "#212E52")
(def chamoisee "#9C8457")
(def dutch-white "#E8D5B0")
(def drab-dark-brown "#524421")

(def resume
  (html
    [:head
     ;; Fetch fonts
     [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
     [:link {:rel         "preconnect"
             :href        "https://fonts.gstatic.com"
             :crossorigin ""}]
     [:link {:href "https://fonts.googleapis.com/css2?family=Inconsolata:wght@200..900&family=Poppins:ital,wght@0,400;0,700;1,400;1,700&display=swap"
             :rel  "stylesheet"}]
     [:title "Benjamin Tan Kuan Wei"]
     [:meta {:name    "description"
             :content "Résumé of Benjamin Tan Kuan Wei"}]
     [:meta {:charset "utf-8"}]
     [:meta {:name    "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:base {:href "/"}]
     [:link {:rel "stylesheet" :href "css/reset.css"}]
     [:style
      (raw-string (css [(at-media {:print true}
                                  [:.hide-in-print {:display :none}])
                        ["@page"
                         {:margin 0
                          :size   :a4}]
                        [:html
                         {:zoom                1.5
                          :background          serenity
                          :font-family         ["Poppins" :sans-serif]
                          :font-optical-sizing :auto}
                         (at-media {:print true}
                                   [:& {:zoom 1}])]
                        [:h1 :h2 :h3 :h4 :h5 :h6
                         {:font-family ["Inconsolata" :monospace]}]
                        [:h1 {:font-size (em 1.2)}]
                        [:body :h1 :h2 :h3 :h4 :h5 :h6 :p
                         {:margin 0}]
                        [:header
                         {:position      :sticky
                          :top           0
                          :z-index       999
                          :background    onyx
                          :color         snow
                          :margin-bottom (em 2)
                          :padding       [[(em 0.5) 0]]}
                         [:section
                          {:margin          [[0 :auto]]
                           :display         :flex
                           :flex-flow       [[:row :wrap]]
                           :gap             (em 0.5)
                           :padding         0
                           :justify-content :space-evenly}
                          [:button {:background    serenity
                                    :color         dark-blue
                                    :border-radius (em 0.5)}]]]
                        [:#resume
                         {:color         onyx
                          :background    snow
                          :max-width     (mm 210)
                          :height        (mm 297)
                          :display       :block
                          :margin        [[0 :auto]]
                          :padding       (in 0.2)
                          :border-radius (em 0.5)}
                         (at-media {:print true}
                                   [:& {:border-radius 0
                                        :background "white"
                                        :color "black"}])
                         [:h1 :h2 :h3 :h4 :h5 :h6
                          {:color dark-blue}]]
                        [:#resume :header
                         {:box-shadow [[0 0 (px 5) onyx]]}]]))]
     [:body
      [:header.hide-in-print
       [:section
        [:h1 "\uD83D\uDDD2 Résumé of Benjamin Tan Kuan Wei \uD83E\uDD17"]
        [:button {:onclick "window.print()"}
         [:h4 "save me as pdf!"]]]]
      [:article#resume
       [:h3 "What's up!"]
       (apply str (repeatedly 8 lorem-ipsum))]]]))

(def pages {"/index.html" (html5 {:lang "en"} resume)})

(defn get-pages [] pages)

(defn get-assets []
  (assets/load-assets "public" ["/css/reset.css" #"/images/.*\.png$"]))

(def app
  (-> (stasis/serve-pages get-pages)
      (optimus/wrap get-assets optimizations/all serve-live-assets)
      wrap-content-type))

(defn -main
  "Serves a live server.
  I probably won't use this since this is just a static site."
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