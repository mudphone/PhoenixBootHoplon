(ns hoaka.channels.core
  (:require
   [hoaka.channels.phoenix :as phx]
   [hoaka.data.state :as st]))



;; Bindings (aka Topic Subscriptions)
(defn phoenix-chan-bindings [pchan event-name]
  (filter #(= (get % "event") event-name)
          (phx/channel-bindings pchan)))

(defn bindings-exist-for-event? [pchan event-name]
  (< 0 (count (phoenix-chan-bindings pchan event-name))))

;; Selector API
(defn connect-channel-cell [pchan event-name cb-cell]
  (if-not (bindings-exist-for-event? pchan event-name)
    (phx/listen-on-channel pchan event-name
                       (fn [msg]
                         (.log js/console "received: " msg)
                         (reset! cb-cell (js->clj msg))))))

(defn phoenix-channels []
  (->> (st/get-phoenix-socket)
       (phx/socket-channels)
       (map (fn [pchan] {:topic (phx/channel-topic pchan)
                         :state (phx/channel-state pchan)
                         :chan pchan}))))

(defn phoenix-channel
  "Reuse channel, or create a new one"
  ([topic-name chan-factory]
   (let [sock (st/get-phoenix-socket)
         current-channel (phoenix-channel topic-name)]
     (if (nil? current-channel)
       (chan-factory sock)
       current-channel)))
  ([topic-name]
   (->> (phoenix-channels)
        (filter #(= (:topic %) topic-name))
        (first))))

(defn setup-channel! [topic-name]
  (phoenix-channel topic-name
                   (fn [sock]
                     (phx/create-join-channel! sock topic-name))))

(defn get-channel [topic-name]
  (:chan (phoenix-channel topic-name)))
