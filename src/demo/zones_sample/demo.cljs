(ns zones-sample.demo
  (:require-macros [zones-sample.logging :refer [log]])
  (:require [clojure.string :as string]
            [zones-sample.boot :refer [boot!]]
            [zones.core :as zones :include-macros true]))

(boot! "/src/demo/zones_sample/demo.cljs")

; --- MEAT STARTS HERE -->
; note: (log ...) expands to (.log js/console ...)

(println "before:" (zones/get v))
(zones/binding [v "I'm a dynamically bound value in the default zone"]
               (println "inside:" (zones/get v))
               (js/setTimeout (zones/bound-fn [] (println "in async call:" (zones/get v))) 500))
(println "after:" (zones/get v))

; <-- MEAT STOPS HERE ---
