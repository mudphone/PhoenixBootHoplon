(ns hoaka.channels.phoenix
  (:require
   [cljsjs.phoenix]
   [cljsjs.phoenix-html]))

;; All Phoenix JS-specific code goes here.
;;===========================================

;; Internal API for Phoenix Channels
;;--------------------------------------
(defn create-socket
  ([]
   (create-socket #js {:logger #(.log js/console (str "w00t! " %1 ": " %2) %3)}))
  ([js-config]
   (new Phoenix.Socket "/socket" js-config)))

(defn connect-socket! [socket]
  (.connect socket)
  socket)

(defn create-channel! [socket topic-name]
  (.channel socket topic-name))

(defn create-join-channel! [socket topic-name]
  (let [chan (create-channel! socket topic-name)]
    (-> chan
        (.join)
        (.receive "ok", (fn [resp]
                          (.log js/console (str "joined the " topic-name " channel") resp)))
        (.receive "error" (fn [reason]
                            (.log js/console "join failed" reason))))
    chan))

(defn socket-channels [socket]
  (js->clj (.-channels socket)))

(defn channel-state [channel]
  (.-state channel))

(defn channel-topic [channel]
  (.-topic channel))

(defn channel-bindings [channel]
  (map js->clj (.-bindings channel)))


;; Constructor API
;;--------------------------------------



;; Selector API
;;--------------------------------------
(defn push-msg [channel event-name payload]
  (.push channel
         event-name
         (clj->js payload))
  (.log js/console "sending message: " payload))

;; Configure Channel
(defn listen-on-channel [pchan event-name callback]
  (.on pchan event-name callback))


