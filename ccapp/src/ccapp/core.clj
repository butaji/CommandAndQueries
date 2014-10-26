(ns ccapp.core)
; (:require [clojure.data.json :as json])

(def id "123")

(def app {

  :send (fn [c]
    (case (c :type)
       :createTask ((handlers :create_task) c)
       :renameTask ((handlers :rename_task) c)
       :completeTask ((handlers :complete_task) c)
    ))

  :read (fn [id]
    {:version 0}
    )

  })

(def eventstore {
  :read (fn[id]
    ; (json/write-str {:a 1 :b 2})
    )
  })

(def repo {
  :get (fn[id]
    (println "getting" id)
    ((eventstore :read) id)
      {
        :id id
      })

  :save (fn[t,v]
      (println "saving" t v)
    )

  })

(def handlers {
  :create_task (fn [c]
      (def t ((repo :get) (c :id)))
      ((task :create) t (c :title))

      ((repo :save) t (c :version))
    )

  :rename_task (fn [c]
      (def t (get repo (c :id)))
      ((task :rename) t (c :title))
      ((repo :save) t (c :version))
      )

  :complete_task (fn [c]
      (def t (get repo (c :id)))
      ((task :complete) t)
      ((repo :save) t (c :version))
      )
  })

(def task {
    :create (fn [t, title]
      {
        :id (t :id)
        :title title
        :type :taskCreated
      }
      )

    :rename (fn [t, title]
      {
        :id (t :id)
        :title (t :title)
        :type :taskRenamed
      }
      )

    :complete (fn [t]
      {
        :id (t :id)
        :type :taskCompleted
      }
      )

  })


((app :send) {
  :id id,
  :title "new item"
  :type :createTask
  :version -1
  })

; (while (not (= ((read app id) :version) 0)) (do
;       (println "waiting for v0")
;   ))

((app :send) {
  :id id,
  :title "new item 1"
  :type :renameTask
  :version 0
  })

; (while (not (= ((read app id) :version) 1)) (do
;       (println "waiting for v1")
;   ))

((app :send) {
  :id id,
  :type :completeTask
  :version 1
  })

; (while (not (= ((read app id) :version) 2)) (do
;       (println "waiting for v2")
;   ))


(println ((app :read) id))
