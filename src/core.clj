(ns core
  (:require [stasis.core :as stasis]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.adapter.jetty :as jetty]
            [hiccup.page :refer [html5]]
            [hiccup2.core :refer [html]]
            [hiccup.util :refer [raw-string]]
            [garden.core :refer [css]]
            [garden.units :refer [cm vw in px mm em percent ch]]
            [garden.color :refer [rgb rgba]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :refer [nth-child &] :as sel]
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
(def magnolia "#F8F4FF")
(def lavender "#D8D1E3")
(def advent-purple "#4B365F")
(def silver "#BDACA4")
(def burnt-orange "#CB6015")

(def cabacus-school [:a {:href "https://cabacus.school"} "cabacus.school"])
(def benjamintan-dev [:a {:href "https://benjamintan.dev"} "benjamintan.dev"])

(def resume
  (html
    [:head
     ;; favicon
     [:link
      {:rel "apple-touch-icon",
       :sizes "180x180",
       :href "/favicon/apple-touch-icon.png"}]
     [:link
      {:rel "icon",
       :type "image/png",
       :sizes "32x32",
       :href "/favicon/favicon-32x32.png"}]
     [:link
      {:rel "icon",
       :type "image/png",
       :sizes "16x16",
       :href "/favicon/favicon-16x16.png"}]
     [:link {:rel "manifest", :href "/favicon/site.webmanifest"}]
     [:link
      {:rel "mask-icon",
       :href "/favicon/safari-pinned-tab.svg",
       :color "#5bbad5"}]
     [:link {:rel "shortcut icon", :href "/favicon/favicon.ico"}]
     [:meta {:name "msapplication-TileColor", :content "#da532c"}]
     [:meta
      {:name "msapplication-config", :content "/favicon/browserconfig.xml"}]
     [:meta {:name "theme-color", :content "#ffffff"}]
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
     [:base {:href "/" :target "_blank"}]
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
                         {:font-family ["Fira Code" "monospace"]}]
                        [:h1 {:font-size (em 1.2)}]
                        [:body :h1 :h2 :h3 :h4 :h5 :h6 :p :ul
                         {:margin      0
                          :font-weight 400}]
                        [:ul {:padding-left (em 1)}]
                        [:a:link :a:visited :a:focus :a:active :a:hover
                         {:color "unset"
                          :text-decoration-color "unset"}]
                        [:footer {:background onyx
                                  :color "white"
                                  :text-align "center"
                                  :padding (em 1)}]
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
                                    :border-radius (em 1)
                                    :padding       [[(em 0.5) (em 1)]]}]]]
                        [:#resume
                         {:color         onyx
                          :background    "white"
                          :max-width     (mm 210)
                          :min-height    (mm 280)
                          :margin        [[(em 2) :auto]]
                          :padding       (em 1)
                          :border-radius (em 0.5)
                          :display       "flex"
                          :flex-flow     [["row" "wrap"]]
                          :gap           (px 1)
                          :font-size     "smaller"}
                         (at-media {:print true}
                                   [:& {:border-radius 0
                                        :margin        0
                                        :position "fixed"
                                        :top (percent 50)
                                        :bottom (percent 50)
                                        :transform "translate(0, -50%)"}])
                         [:.dark-orange {:color burnt-orange}]
                         [:&>section {:padding       (em 1)
                                      :border-radius (em 0.5)}
                          [:&:first-child {:flex 2}
                           (mobile-view [:& {:flex-basis "max-content"}])]
                          [(& (nth-child "2")) {:flex 5}]
                          [:&.intro {:background magnolia
                                     :display    "flex"
                                     :flex-flow  [["column" "wrap"]]
                                     :gap        (em 1)}
                           (conj headers {:color advent-purple})]
                          [:&.body {:background floral-white
                                    :display    "flex"
                                    :flex-flow  [["column" "wrap"]]
                                    :gap        (em 1)}
                           (conj headers {:color burnt-orange})
                           [:.monospace {:font-family ["Fira Code" "monospace"]
                                         :color burnt-orange}]]]]
                        [:.box-shadow
                         {:box-shadow [[0 0 (px 5) onyx]]}
                         (at-media {:print true}
                                   [:& {:box-shadow "none"}])]
                        [:#display-profile {:display       "inline"
                                            :max-width     (em 2)
                                            :border-radius (percent 50)}]
                        [:.text-row {:display     "flex"
                                     :flex-flow   [["row" "wrap"]]
                                     :align-items "center"
                                     ;:justify-content "space-between"
                                     :gap         (em 0.5)}]
                        [:.logo {:display   "inline"
                                 :max-width (em 1)}]]))]
     [:body
      [:header.hide-in-print.box-shadow
       [:section
        [:div
         [:img#display-profile {:src "images/display_profile.jpg"}]
         [:h1 {:style {:display "inline"}}
          "Benjamin Tan's résumé \uD83E\uDD17"]]
        [:button {:onclick "window.print()"}
         [:h5 "\uD83D\uDDA8\uFE0F or \uD83D\uDCBE me as pdf!"]]]]
      [:article#resume.box-shadow
       [:section.intro
        [:h3 [:b "Benjamin Tan Kuan Wei"]]
        [:section
         [:h5 benjamintan-dev]
         [:a.text-row {:href "https://github.com/betakuwe"}
          [:img.logo {:src "images/github-mark.svg"}]
          [:h5 "betakuwe"]]
         [:a.text-row {:href "https://www.linkedin.com/in/benjamin-tan-2b06a0127/"}
          [:img.logo {:src "images/linkedin.svg"}]
          [:h5 "benjamin-tan-2b06a0127"]]
         [:a {:href "mailto:betakuwe@gmail.com"}
          [:h5 "betakuwe@gmail.com"]]
         [:h5 "+65 9271 6970"]
         [:h5 "Ang Mo Kio, Singapore"]
         [:h5 "Singapore Citizen"]]

        [:h3 [:b "Programming languages"]]
        [:section
         [:h5 [:b "Used extensively"]]
         [:p "Dart, Clojure, Python, Java, C#, JavaScript, YAML, HTML, CSS"]]
        [:section
         [:h5 [:b "Learnt but inexperienced"]]
         [:p "SQL, Golang, TypeScript, C/C++, Elixir, Prolog"]]

        [:h3 [:b "Frameworks/Tools used"]]
        [:section
         [:p "Flutter, Android Studio, .NET, Unity Engine, Jira, Confluence, draw.io, GitHub Workflows"]]

        [:h3 [:b "Education"]]
        [:section
         [:h5 [:b "National University of Singapore (NUS)"]]
         [:p "Bachelor of Computing"]
         [:p "June 2023"]
         [:p "Honours (Highest Distinction)"]
         [:p "Grade: 4.52/5.00"]]

        [:h3 [:b "Work"]]
        [:section
         [:h5 [:b "BoostDraft Inc."]]
         [:p "Software Engineer (Part-time)"]
         [:p "Remote"]
         [:p "Jan 2024 - Mar 2024"]]
        [:section
         [:h5 [:b "OSIM Pte. Ltd."]]
         [:p "Mobile Engineer (Mid level)"]
         [:p "Singapore"]
         [:p "Jul 2023 - Jan 2024"]]
        [:section
         [:h5 [:b "NUS Industrial Systems Engineering and Management"]]
         [:p "IT Developer (Intern)"]
         [:p "Singapore"]
         [:p "May 2020 - Jul 2020"]]

        [:h3 [:b "Languages"]]
        [:section
         [:p "English, Mandarin Chinese, French"]]]

       ;; Body
       [:section.body
        [:h3 [:b "About me"]]
        [:section
         [:p "Software engineer available for hire.
         I have extensive experience in Flutter and mobile development, also in using git, GitHub, YML workflows in GitHub actions, Python and shell scripting for software development on unix/linux systems, and Jira and Confluence for project management.
         I spend my free time working on my projects and learning about new frameworks and programming languages.
         My projects include " cabacus-school " and " benjamintan-dev ", a website made with Clojure."]]

        [:h3 [:b "Creator of " cabacus-school]]
        [:section
         [:ul
          [:li "Educational app made with Flutter available on Android, iOS (soon), and web."]
          [:li "I make " [:i "everything"] ". Including the image and audio assets and the website."]
          [:li "Uses " [:a {:href "https://pub.dev/packages/state_beacon"} "state_beacon"] " for state management.
          Migrated from " [:a {:href "https://pub.dev/packages/provider"} "provider"] " and " [:a {:href "https://pub.dev/packages/mobx"} "MobX"] "."]]]

        [:h3 [:b ".NET Software Engineer at BoostDraft Inc."]]
        [:section
         [:ul
          [:li "Develop .NET apps in C# for Microsoft Word using " [:a {:href "https://learn.microsoft.com/en-us/visualstudio/vsto/create-vsto-add-ins-for-office-by-using-visual-studio"} "VSTO API"] "."]
          [:li "Implement frontend features using " [:a {:href "https://learn.microsoft.com/en-us/dotnet/desktop/wpf"} "Windows Presentation Framework"] "."]
          [:li "Use machine learning for NLP parsing in Microsoft Word."]]]

        [:h3 [:b "Flutter Mobile Engineer at OSIM Pte. Ltd."]]
        [:section
         [:ul
          [:li "Develop Flutter apps to connect to and operate bluetooth devices."]
          [:li "Implement CI/CD with GitHub workflows and Python scripts to integrate with backend services."]
          [:li "Develop and publish the " [:a {:href "https://apps.apple.com/sg/app/osim-well-being/id1588337384"} "OSIM Well-Being App"] "."]]]

        [:h3 [:b "Academics"]]

        [:section
         [:h5 [:b "IT Developer Internship at NUS Industrial Systems Engineering and Management"]]
         [:p "Research and prototype a general solver for a class of optimization problems in Constraint Programming to create an automated validation tool for algorithm outputs."]]

        [:section
         [:h5 [:b "HCI Research Project at NUS-HCI Lab"]]
         [:p "Design UI features using " [:a {:href "https://unity.com/"} "Unity Engine"] " in C# to create prototypes for Mixed Reality using the " [:a {:href "https://www.xreal.com/"} "XREAL AR glasses"] " and " [:a {:href "https://www.microsoft.com/en-us/hololens"} "Microsoft Hololens"] "."]]

        [:section
         [:h5 [:b "Uncertainty Modeling in AI Project at NUS"]]
         [:p "Design Bayesian and Markov Networks in Python to find patterns in national policies in pandemic management between various countries during the COVID-19 pandemic."]]

        [:section
         [:h5 [:b "Neural Networks and Deep Learning Project at NUS"]]
         [:p "Develop pipelines with convolutional and recursive neural networks in " [:a {:href "https://pytorch.org/"} "PyTorch"] " to automate hyperparameter tuning in an NLP task."]]

        [:h3 [:b "Hackathons"]]

        [:section
         [:h5 [:b "KIRO 2019 Concours Inter-écoles de Recherche Opérationnelle at Télécom Paris"]]
         [:p "Implement Simulated Annealing and other machine learning techniques to solve a non-trivial graph theory problem."]]

        [:section
         [:h5 [:b "Hack&Roll 2019 at NUS"]]
         [:p "Develop a multiplayer game on web browser controlled using accelerometers in smartphones using " [:a {:href "https://webrtc.org/"} "WebRTC"] "."]]]]

      [:footer.hide-in-print.box-shadow
       [:small
        [:h5 "Made by me"]
        [:h5 "I made this website"]
        [:h5 "All rights reserved by me"]]]]]))

(def pages {"/index.html" (html5 {:lang "en"} resume)})

(defn get-pages [] pages)

(defn get-assets []
  (assets/load-assets "public" ["/css/reset.css"
                                ;#"/images/.*\.png$"
                                #"/images/.*\.svg$"
                                #"/images/.*\.jpg$"
                                #"/favicon/.*"
                                ]))

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