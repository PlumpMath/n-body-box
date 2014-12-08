(ns nbodybox.core
  (:require [enfocus.core :as ef]
            [enfocus.events :as events]
            [enfocus.effects :as effects])
  (:require-macros [enfocus.macros :as em]))


(def app-name "NBodyBox")
(def username (or js/process.env.USERNAME js/process.env.USER))


(em/deftemplate main-content :compiled
  "resources/public/templates/main-content.html"
  [username]
  [".username"] (ef/content username))


(defn add-main-content! []
  "Add main content"
  (ef/at ["body > .container"] (ef/content (main-content username))))


(defn start []
  "Entry point.  Called when page is loaded"
  (add-main-content!))


;; Using window onload instead of calling a main method from client
(set! (.-onload js/window) start)
;; Close window after 1 second
(.setTimeout js/window #(.close js/window) 4000)
