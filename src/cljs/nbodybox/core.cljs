(ns nbodybox.core
  (:require [enfocus.core :as ef]
            [enfocus.events :as events]
            [enfocus.effects :as effects])
  (:require-macros [enfocus.macros :as em]))


;; Define some constants
(def app-name "NBodyBox")
(def username (or js/process.env.USERNAME js/process.env.USER))


(defn create-menu! []
  "Creates the OS menu"
  ;; Use requirejs to include the gui module
  (let [nw (js/require "nw.gui")
        win (.get (.-Window nw))
        mb (nw.Menu. (js-obj "type" "menubar"))]
        (if (= (.-platform js/process) "darwin") (.createMacBuiltin mb
                                                   app-name))
    (set! (.-menu win) mb)))


(em/deftemplate main-content :compiled
  "resources/public/templates/main-content.html"
  [username]
  [".username"] (ef/content username))


(em/defaction page-change [content nav-ele]
  [".starter-template"] (ef/content content)
  [nav-ele] (ef/add-class "active")
  [(str "nav li:not(" nav-ele ")")] (ef/remove-class "active"))


(em/defaction attach-nav-handlers! []
  ["nav .main"] (events/listen :click
                               #(page-change (main-content username) ".main")))


(defn update-greeting! []
  "Set the username to the Node process user"
  (ef/at [".username"] (ef/content username)))


(defn add-main-content! []
  "Add main content"
  (ef/at ["body > .container"] (ef/content (main-content username))))


(defn start []
  "Entry point.  Called when page is loaded"
  (create-menu!)
  (add-main-content!)
  (update-greeting!)
  (attach-nav-handlers!))


;; Using window onload instead of calling a main method from client
(set! (.-onload js/window) start)
