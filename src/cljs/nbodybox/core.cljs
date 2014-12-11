(ns nbodybox.core
  (:require [enfocus.core :as ef]
            [enfocus.events :as events]
            [enfocus.effects :as effects])
  (:require-macros [enfocus.macros :as em]))


(def app-name "NBodyBox")
(def username (or js/process.env.USERNAME js/process.env.USER))
(def gui (js/require "nw.gui"))


(em/deftemplate main-content :compiled
  "resources/public/templates/main-content.html"
  [username]
  [".username"] (ef/content username))


(defn add-main-content!
  "
  Add main content
  "
  []
  (ef/at ["body > .container"] (ef/content (main-content username))))


(defn start []
  "Entry point.  Called when page is loaded"
  (add-main-content!))


(defn swap-splash-for-main-window []
  (.close js/window)
  (.open gui.Window "main.html" #js {:toolbar false
                                     :width 1000
                                     :height 600}))


;; Using window onload instead of calling a main method from client
(set! (.-onload js/window) start)

;; Close splash screen after a delay; launch main window.
(.setTimeout js/window swap-splash-for-main-window 4000)
