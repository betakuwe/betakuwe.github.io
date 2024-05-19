(ns core
  (:require [stasis.core :as stasis]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.adapter.jetty :as jetty]
            [hiccup.page :refer [html5]]
            [hiccup2.core :refer [html]]
            [hiccup.util :refer [raw-string]]
            [garden.core :refer [css]]
            [garden.units :refer [cm vw in px mm em percent]]
            [garden.color :refer [rgb rgba]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :refer [nth-child] :as s]
            [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]
            [talltale.core :refer [lorem-ipsum]]))

(def headers [:h1 :h2 :h3 :h4 :h5 :h6])
(defn mobile-view [body]
  (at-media {:max-width (px 600)} body))

;; https://coolors.co/393d3f-fdfdff-c6c5b9-595934
(def onyx "#393D3F")
(def floral-white "#FFFAF0")
(def lavender "#D8D1E3")
(def advent-purple "#4B365F")
(def silver "#BDACA4")
(def burnt-orange "#CB6015")
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
     [:link {:href "https://fonts.googleapis.com/css2?family=Fira+Code:wght@300..700&family=Fira+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap"
             :rel  "stylesheet"}]
     [:title "Benjamin Tan's résumé"]
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
                          :background          silver
                          :font-family         ["Fira Sans" :sans-serif]
                          :font-optical-sizing :auto}
                         (at-media {:print true}
                                   [:& {:zoom                       1
                                        :-webkit-print-color-adjust "exact"
                                        :print-color-adjust         "exact"}])
                         (mobile-view [:& {:zoom 1}])]
                        [:h1 :h2 :h3 :h4 :h5 :h6
                         {:font-family ["Fira Code" :monospace]}]
                        [:h1 {:font-size (em 1.2)}]
                        [:body :h1 :h2 :h3 :h4 :h5 :h6 :p
                         {:margin      0
                          :font-weight 400}]
                        [:header
                         {:position   :sticky
                          :top        0
                          :z-index    999
                          :background onyx
                          :color      "white"
                          :padding    [[(em 0.5) 0]]}
                         [:section
                          {:margin          [[0 :auto]]
                           :display         :flex
                           :flex-flow       [[:row :wrap]]
                           :gap             (em 0.5)
                           :padding         0
                           :justify-content :space-evenly}
                          [:div {:display     "flex"
                                 :flex-flow   [["row" "wrap"]]
                                 :gap         (em 1)
                                 :align-items "center"}]
                          [:button {:background    lavender
                                    :color         advent-purple
                                    :border-radius (em 0.5)
                                    :padding       [[0 (em 1)]]}]]]
                        [:#resume
                         {:color         onyx
                          :background    "white"
                          :max-width     (mm 210)
                          :height        (mm 280)
                          :margin        [[(em 2) :auto]]
                          :padding       (em 1)
                          :border-radius (em 0.5)
                          :display       "flex"
                          :flex-flow     [["row" "wrap"]]
                          :gap           (px 1)
                          :font-size     "smaller"}
                         (at-media {:print true}
                                   [:& {:border-radius 0
                                        :margin        0}])
                         [:h1 :h2 :h3 :h4 :h5 :h6
                          {:color advent-purple}]
                         [:.dark-orange {:color burnt-orange}]
                         [:section {:padding       (em 1)
                                    :border-radius (em 0.5)}
                          [:&:first-child {:flex 2}
                           (mobile-view [:& {:flex-basis "max-content"}])]
                          [(& (nth-child "2")) {:flex 5}]
                          [:&.intro {:background "#F8F4FF"}]
                          [:&.body {:background floral-white}]]]
                        [:.box-shadow
                         {:box-shadow [[0 0 (px 5) onyx]]}
                         (at-media {:print true}
                                   [:& {:box-shadow "none"}])]
                        [:#display-profile {:display       "inline"
                                            :max-width     (em 2)
                                            :border-radius (percent 50)}]
                        [:.text-row {:display         "flex"
                                     :flex-flow       [["row" "wrap"]]
                                     :align-items     "center"
                                     ;:justify-content "space-between"
                                     :gap             (em 0.5)}]
                        [:.logo {:display   "inline"
                                 :max-width (em 1)}]]))]
     [:body
      [:header.hide-in-print.box-shadow
       [:section
        [:div
         [:img#display-profile {:src "images/display_profile.png"}]
         [:h1 {:style {:display "inline"}}
          "Benjamin Tan's résumé \uD83E\uDD17"]]
        [:button {:onclick "window.print()"}
         [:p "save me as pdf!"]]]]
      [:article#resume.box-shadow
       [:section.intro
        [:h3 [:b "Benjamin Tan Kuan Wei"]]
        [:div {:style {:line-height 1.75}}
         [:a {:href "https://benjamintan.dev"}
          [:h5 "benjamintan.dev"]]

         [:a.text-row {:href "https://github.com/betakuwe"}
          [:img.logo {:src "images/github-mark.svg"}]
          [:h6 "betakuwe"]]
         [:a.text-row {:href "https://www.linkedin.com/in/benjamin-tan-2b06a0127/"}
          [:img.logo {:src "images/linkedin.svg"}]
          [:h6 "benjamin-tan-2b06a0127"]]

         [:a {:href "mailto:working.celery@gmail.com"}
          [:h6 "working.celery@gmail.com"]]
         [:h6 "+65 9271 6970"]

         [:h6 "Ang Mo Kio, Singapore"]
         [:h6 "Singapore Citizen"]]


        (lorem-ipsum)]
       [:section.body
        [:h3.dark-orange "Hi there."]
        (apply str (repeatedly 2 lorem-ipsum))]]]]))

(def pages {"/index.html" (html5 {:lang "en"} resume)})

(defn get-pages [] pages)

(defn get-assets []
  (assets/load-assets "public" ["/css/reset.css"
                                #"/images/.*\.png$"
                                #"/images/.*\.svg$"]))

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