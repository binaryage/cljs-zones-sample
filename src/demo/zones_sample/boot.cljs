(ns zones-sample.boot
  (:require-macros [cljs.core.async.macros :refer [go]]
                   [zones-sample.logging :refer [log]]
                   [devtools.util :refer [ocall oget oset oapply]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure.string :as string]))

(defn extract-meat [re s]
  (let [rex (js/RegExp. re "igm")]
    (.exec rex s)))

(defn trim-newlines [s]
  (let [rex (js/RegExp. "^\n+|\n+$" "g")]
    (.replace s rex "")))

(defn escape-html [text] (string/escape text {\< "&lt;", \> "&gt;", \& "&amp;"}))

(defn get-meat [source-code]
  (trim-newlines (nth (extract-meat (str "-" "->([^]*?); <-") source-code) 1)))

(defn fetch-source-code [source-path]
  (go
    (let [response (<! (http/get source-path))
          block (.querySelector js/document "code")]
      (aset block "innerHTML" (escape-html (get-meat (:body response))))
      (ocall js/hljs "highlightBlock" block))))

(defn boot! [source-path]
  (enable-console-print!)
  (fetch-source-code source-path))

(enable-console-print!)
