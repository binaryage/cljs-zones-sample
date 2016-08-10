(ns zones-sample.demo
  (:require-macros [zones-sample.logging :refer [log]])
  (:require [zones-sample.boot :refer [boot!]]
            [zones.core :as zones :include-macros true]))

(boot! "/src/demo/zones_sample/demo.cljs")

; --- MEAT STARTS HERE -->
; note: (log ...) expands to (.log js/console ...)

(log "before:" (zones/get v))
(zones/binding [v "I'm a dynamically bound value in the default zone"]
               (log "inside:" (zones/get v))
               (js/setTimeout (zones/bound-fn [] (log "in async call:" (zones/get v))) 500))
(log "after:" (zones/get v))

; <-- MEAT STOPS HERE ---
