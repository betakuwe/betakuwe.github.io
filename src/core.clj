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
(def russet "#85431E")

(def cabacus-school [:a {:href "https://cabacus.school"} "cabacus.school"])
(def benjamintan-dev [:a {:href "https://benjamintan.dev"} "benjamintan.dev"])

(def resume
  (html
   [:head
     ;; favicon
    [:link
     {:rel   "apple-touch-icon",
      :sizes "180x180",
      :href  "/favicon/apple-touch-icon.png"}]
    [:link
     {:rel   "icon",
      :type  "image/png",
      :sizes "32x32",
      :href  "/favicon/favicon-32x32.png"}]
    [:link
     {:rel   "icon",
      :type  "image/png",
      :sizes "16x16",
      :href  "/favicon/favicon-16x16.png"}]
    [:link {:rel "manifest", :href "/favicon/site.webmanifest"}]
    [:link
     {:rel   "mask-icon",
      :href  "/favicon/safari-pinned-tab.svg",
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
                       [:h2 {:font-size (em 1.17)}]
                       [:h3 :.mono {:font-size (em 0.83)}]
                       [:.mono :h1 :h2 :h3 :h4 :h5 :h6
                        {:font-family ["Fira Code" "monospace"]}]
                       [:h1 {:font-size (em 1.2)}]
                       [:body :h1 :h2 :h3 :h4 :h5 :h6 :p :ul
                        {:margin 0}]
                       [:ul {:padding-left (em 1)}]
                       [:a:link :a:visited :a:focus :a:active :a:hover
                        {:color                 "unset"
                         :text-decoration-color "unset"}]
                       [:footer {:background onyx
                                 :color      "white"
                                 :text-align "center"
                                 :padding    (em 1)}]
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
                                       :position      "fixed"
                                       :top           (percent 50)
                                       :bottom        (percent 50)
                                       :transform     "translate(0, -50%)"}])
                        [:.dark-orange {:color russet}]
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
                          (conj headers {:color russet})]]]
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
                                :max-width (em 1)}]
                       [:#ideal-candidate {:font-size (px 1)
                                           :color floral-white}]]))]
    [:body
     [:header.hide-in-print.box-shadow
      [:section
       [:div
        [:img#display-profile {:src "images/display_profile.jpg"
                               :alt "Benjamin Tan's display profile"}]
        [:h1 {:style {:display "inline" :font-weight 400}}
         "Benjamin Tan's résumé \uD83E\uDD17"]]
       [:button {:onclick "window.print()"}
        [:p.mono "\uD83D\uDDA8\uFE0F or \uD83D\uDCBE me as pdf!"]]]]
     [:article#resume.box-shadow
      [:section.intro
       [:h2 "Benjamin Tan Kuan Wei"]
       [:section
        [:p.mono benjamintan-dev]
        [:a.text-row {:href "https://github.com/betakuwe"}
         [:img.logo {:src "images/github-mark.svg"
                     :alt "GitHub logo"}]
         [:p.mono "betakuwe"]]
        [:a.text-row {:href "https://www.linkedin.com/in/benjamin-tan-2b06a0127/"}
         [:img.logo {:src "images/linkedin.svg"
                     :alt "LinkedIn Logo"}]
         [:p.mono "benjamin-tan-2b06a0127"]]
        [:a {:href "mailto:betakuwe@gmail.com"}
         [:p.mono "betakuwe@gmail.com"]]
        [:p.mono "+65 9271 6970"]
        [:p.mono "Ang Mo Kio, Singapore"]
        [:p.mono "Singapore Citizen"]]

       [:h2 "Programming languages"]
       [:section
        [:h3 "Used Professionally"]
        [:p "Dart, Python, C/C++, Java, C#, JavaScript, HTML, CSS, SQL"]]
       [:section
        [:h3 "Used for fun / hobby projects"]
        [:p "Clojure, Elixir, Prolog"]]

       [:h2 "Frameworks/Tools used"]
       [:section
        [:p "Flutter, Android Studio, .NET, Unity Engine, Jira, Confluence, draw.io, GitHub Workflows, Selenium, Crawlee"]]

       [:h2 "Education"]
       [:section
        [:h3 "National University of Singapore (NUS)"]
        [:p "Bachelor of Computing"]
        [:p "June 2023"]
        [:p "Honours (Highest Distinction)"]
        [:p "Grade: 4.52/5.00"]]

       [:h2 "Work"]
       [:section
        [:h3 "KLASS Engineering"]
        [:p "Software Engineer"]
        [:p "Singapore"]
        [:p "July 2024 - Present"]]
       [:section
        [:h3 "BoostDraft Inc."]
        [:p "Software Engineer (Part-time)"]
        [:p "Remote"]
        [:p "Jan 2024 - Mar 2024"]]
       [:section
        [:h3 "OSIM Pte. Ltd."]
        [:p "Mobile Engineer"]
        [:p "Singapore"]
        [:p "Jul 2023 - Jan 2024"]]
       ; [:section
       ;  [:h3 "NUS Industrial Systems Engineering and Management"]
       ;  [:p "IT Developer (Intern)"]
       ;  [:p "Singapore"]
       ;  [:p "May 2020 - Jul 2020"]]

       [:h2 "Languages"]
       [:section
        [:p "English, Mandarin Chinese, French"]]
       [:section
        [:p "I can do one-leg squats, AKA pistol squats."]]]

       ;; Body
      [:section.body
       [:h2 "About me"]
       [:section
        [:p "Software engineer available for hire.
         I have extensive experience in Flutter and mobile development, also in using git, CI/CD in GitHub/GitLab, Python and shell scripting for software development on unix/linux systems, and Jira and Confluence for project management.
         I spend my free time learning about new frameworks and programming languages, and working on fun projects.
         My projects include " cabacus-school " and " benjamintan-dev ", both made with Clojure because it's fun."]]

       [:h2 "Creator of " cabacus-school]
       [:section
        [:ul
         [:li "Educational math app made with Flutter available on Android, iOS, and web."]
         [:li "I make " [:i "everything"] ". Including the image and audio assets and the website."]
         [:li "Uses " [:a {:href "https://github.com/Tensegritics/ClojureDart"} "ClojureDart"] " to transpile to Dart."]]]

       [:h2 "Software Engineer at KLASS Engineering"]
       [:section
        [:ul
         [:li "Build mobile apps using Flutter to use mobile devices as IoT devices."]
         [:li "Run machine learning models entire on device offline, i.e. llama.cpp, ONNX, TVM."]
         [:li "Build FFI interfaces for C++ libraries of llama.cpp, ONNX, TVM from Flutter."]]]

       [:h2 ".NET Software Engineer at BoostDraft Inc."]
       [:section
        [:ul
         [:li "Develop .NET apps in C# for Microsoft Word using " [:a {:href "https://learn.microsoft.com/en-us/visualstudio/vsto/create-vsto-add-ins-for-office-by-using-visual-studio"} "VSTO API"] "."]
         [:li "Implement frontend features using " [:a {:href "https://learn.microsoft.com/en-us/dotnet/desktop/wpf"} "Windows Presentation Framework"] "."]
         [:li "Use machine learning for NLP parsing in Microsoft Word."]]]

       [:h2 "Flutter Mobile Engineer at OSIM Pte. Ltd."]
       [:section
        [:ul
         [:li "Develop Flutter apps to connect to and operate bluetooth devices seamlessly."]
         [:li "Implement CI/CD with GitHub workflows and Python scripts to integrate with backend services."]
         [:li "Develop and publish the " [:a {:href "https://apps.apple.com/sg/app/osim-well-being/id1588337384"} "OSIM Well-Being App"] "."]]]

       [:h2 "Academics"]

       ; [:section
       ;  [:h3 "IT Developer Internship at NUS Industrial Systems Engineering and Management"]
       ;  [:p "Research and prototype a general solver for a class of optimization problems in Constraint Programming to create an automated validation tool for algorithm outputs."]]

       [:section
        [:h3 "HCI Research Project at NUS-HCI Lab"]
        [:p "Design UI features using " [:a {:href "https://unity.com/"} "Unity Engine"] " in C# to create prototypes for Mixed Reality using the " [:a {:href "https://www.xreal.com/"} "XREAL AR glasses"] " and " [:a {:href "https://www.microsoft.com/en-us/hololens"} "Microsoft Hololens"] "."]]

       [:section
        [:h3 "Uncertainty Modeling in AI Project at NUS"]
        [:p "Design Bayesian and Markov Networks in Python to find patterns in national policies in pandemic management between various countries during the COVID-19 pandemic."]]

       [:section
        [:h3 "Neural Networks and Deep Learning Project at NUS"]
        [:p "Develop pipelines with convolutional and recursive neural networks in " [:a {:href "https://pytorch.org/"} "PyTorch"] " to automate hyperparameter tuning in an NLP task."]
        [:p#ideal-candidate "Technical Skills: Agile methodologies, DevOps, Continuous Integration/Continuous Deployment (CI/CD), RESTful APIs, Microservices architecture, Test-Driven Development (TDD), Docker, Kubernetes, AWS, Azure, Google Cloud Platform (GCP), SQL, NoSQL, MongoDB, PostgreSQL, Redis, Jenkins, Git, GitHub, GitLab, Bitbucket, JIRA, Confluence, Selenium, JUnit, Mockito, JSON, XML, HTML, CSS, React, Angular, Vue.js. Certifications: AWS Certified Solutions Architect, Google Cloud Professional Data Engineer, Microsoft Certified: Azure Developer Associate, Certified Kubernetes Administrator (CKA), Certified ScrumMaster (CSM), Oracle Certified Professional, Java SE 11 Developer. Experience Highlights:. Led a cross-functional team of 10+ engineers in developing a scalable microservices-based application, improving deployment speed by 40%. Implemented a CI/CD pipeline using Jenkins and Docker, reducing deployment times by 50%. Migrated legacy systems to cloud infrastructure (AWS/GCP/Azure), achieving 99.9% uptime and reducing operational costs by 30%. Developed RESTful APIs consumed by millions of users, ensuring high performance and low latency. Conducted comprehensive code reviews and implemented TDD and pair programming practices, enhancing code quality and team collaboration. Soft Skills: Leadership, Project Management, Problem-solving, Communication, Team Collaboration, Adaptability, Critical Thinking, Innovation, Time Management."]]

       [:h2 "Hackathons"]

       [:section
        [:h3 "KIRO 2019 Concours Inter-écoles de Recherche Opérationnelle at Télécom Paris"]
        [:p "Implement Simulated Annealing and other machine learning techniques to solve a non-trivial graph theory problem."]]

       [:section
        [:h3 "Hack&Roll 2019 at NUS"]
        [:p "Develop a multiplayer game on web browser controlled using accelerometers in smartphones using " [:a {:href "https://webrtc.org/"} "WebRTC"] "."]]]]

     [:footer.hide-in-print.box-shadow
      [:small
       [:p.mono "Made by me"]
       [:p.mono "I made this website"]
       [:p.mono "All rights reserved by me"]]]]]))

(def pages {"/index.html" (html5 {:lang "en"} resume)})

(defn get-pages [] pages)

(defn get-assets []
  (assets/load-assets "public" ["/css/reset.css"
                                ;#"/images/.*\.png$"
                                #"/images/.*\.svg$"
                                #"/images/.*\.jpg$"
                                #"/favicon/.*"]))

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
