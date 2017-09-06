(ns hoaka.data.state
  (:require
   [javelin.core :refer [cell] :refer-macros [cell= defc defc=]]))

(def CHAT-LOBBY-TOPIC "rooms:lobby")

(defonce app-state (cell
                    {:phoenix {:socket nil
                               :channels {:chat-lobby CHAT-LOBBY-TOPIC}}}))

;; Phoenix Channel
(def PHOENIX-SOCKET-PATH [:phoenix :socket])
;;(defc= phoenix-channel-names (get-in app-state [:phoenix :channels]))
;;(defc= chat-lobby-channel-topic-name (:chat-lobby phoenix-channel-names))

(defn get-phoenix-socket
  "Return existing socket, or create a new one"
  ([sock-factory]
   (swap! app-state #(update-in % PHOENIX-SOCKET-PATH
                                (fn [current-sock]
                                  (if (nil? current-sock)
                                    (sock-factory)
                                    current-sock))))
   (get-phoenix-socket))
  ([]
   (get-in @app-state PHOENIX-SOCKET-PATH)))

