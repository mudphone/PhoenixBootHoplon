(def TARGET-DIR "priv/static")

(set-env!
 :resource-paths #{"cljs/src"}
 :source-paths #{"assets/scss"}
 :asset-paths #{"assets/static"
                "assets/vendor"}
 :dependencies '[[deraen/boot-sass            "0.3.1"]
                 [danielsz/boot-autoprefixer  "0.1.0"]
                 [adzerk/boot-cljs            "1.7.228-1"]
                 [org.slf4j/slf4j-nop         "1.7.13" :scope "test"]
                 [adzerk/boot-reload          "0.5.1"]
                 [hoplon/hoplon               "7.0.1"]
                 [org.clojure/clojure         "1.8.0"]
                 [org.clojure/clojurescript   "1.9.293"]
                 [cljsjs/bootstrap            "3.3.6-1"]
                 [cljsjs/jquery               "2.2.4-0"]
                 [cljsjs/phoenix              "1.3.0-0"]
                 [cljsjs/phoenix-html         "2.10.4-0"]
                 [adzerk/boot-cljs-repl   "0.3.3"  :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel                  "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require '[deraen.boot-sass      :refer [sass]]
         '[danielsz.autoprefixer :refer [autoprefixer]]
         '[adzerk.boot-cljs      :refer [cljs]]
         '[adzerk.boot-reload    :refer [reload]]
         '[hoplon.boot-hoplon    :refer [hoplon prerender]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

(task-options!
 autoprefixer {:files ["app.css"]
               :exec-path "node_modules/.bin/postcss"
               :browsers "last 2 versions"}
 repl {:middleware '[cemerick.piggieback/wrap-cljs-repl]})

(deftask sift-js-and-css
  "Move my custom JS and CSS files to specific target dirs."
  []
  (comp
   (sift :move {#"^([^/]*\.js(\.map)?)$" "js/$1"})
   (sift :move {#"(.*/)?(.*\.css(\.map)?)$" "css/$2"})))

(deftask build
  "Build for production."
  []
  (comp
   (hoplon)
   (cljs)
   (sass)
   (autoprefixer)
   (sift-js-and-css)
   (target :dir #{TARGET-DIR})))

(deftask dev
  "Build loop for local development."
  []
  (comp
   (watch :verbose true)
   (speak)
   (hoplon)
   (reload)
   (cljs-repl)
   (cljs)
   (sass)
   (autoprefixer)
   (sift-js-and-css)
   (target :dir #{TARGET-DIR})))
