(ns hoaka.channels.userland
  (:require
   [hoaka.channels.core :as ch]
   [hoaka.channels.phoenix :as phx]
   [hoaka.data.state :as st]))

(defn push-chat-lobby-msg [event-name payload]
  (phx/push-msg (ch/get-channel st/CHAT-LOBBY-TOPIC)
                event-name
                payload)) 

(defn send-chat-lobby-msg [payload]
  (push-chat-lobby-msg "chat" {:msg payload}))


;; Chat Lobby Channel
(defn setup-chat-lobby-channel! []
  (ch/setup-channel! st/CHAT-LOBBY-TOPIC))

(defn get-chat-lobby-channel []
  (ch/get-channel st/CHAT-LOBBY-TOPIC))

(defn setup-socket! []
  (st/get-phoenix-socket (fn []
                           (-> (phx/create-socket)
                               (phx/connect-socket!)))))

;; Connect
(defn connect-channel-to-cell [channel event-name cb-cell]
  (ch/connect-channel-cell channel
                           event-name
                           cb-cell))

(defn connect-chat-cell
  [cb-cell]
  (connect-channel-to-cell (get-chat-lobby-channel) "chat" cb-cell))
