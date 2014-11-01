(ns app.core
  (:require [clj-http.client :as client]))

(def task {
    :create (fn [t, title]
      ; (println "task:create" t title)
      {
        :id (t :id)
        :title title
        :type :taskCreated
      }
    )

    :rename (fn [t, title]
      ; (println "task:rename" t title)
      {
        :id (t :id)
        :title title
        :type :taskRenamed
      }
    )

    :complete (fn [t]
      ; (println "task:complete" t)
      {
        :id (t :id)
        :type :taskCompleted
        :completed true
      }
    )

    :apply (fn [t, e]
      ; (println "task:apply" t e)

      (case (:type e)
        :taskCreated {
            :id (t :id)
            :title (t :title)
          }
        :taskRenamed {
            :id (t :id)
            :title (t :title)
            :completed (t :completed)
          }
        :taskCompleted {
            :id (t :id)
            :completed (t :completed)
          }
        t
      )

    )
})

(def eventstore {
  :load (fn[id]
    ; (println "eventstore:load" id)

    (def res ((client/get id {:as :json}) :body))
    (if res res ())
  )

  :save (fn[id e v]
    ; (println "eventstore:save" id e v)

    (def events ((eventstore :load) id))

    (def ev (apply merge e { :version (count events) }))

    (def data (conj
        events
        ev
      )
    )

    (client/put id {
      :form-params data
      :content-type :json
    })

    (println ev)
    ev
  )

})



(def listeners (ref #{}))
(def eventbus {

    :subscribe (fn[h]
      (dosync
         (alter listeners conj h)))

    :publish (fn[e]
      (doall
        (map #(future (% e)) @listeners)))
})

(def repo {
  :get (fn[id]
    ; (println "repo:get" id)

    (def events ((eventstore :load) id))

    ; (println "events for " id events)

    (reduce
      #((task :apply) %1 %2)
      { :id id }
      events)
  )

  :save (fn[id,e,v]
      ; (println "repo:save" id e v)

      (def ev ((eventstore :save) id e v))
      ((eventbus :publish) ev)
  )

})

(def handlers {
  :create_task (fn [c]
      ; (println "handlers:create_task" c)
      (def t ((repo :get) (c :id)))
      (def e ((task :create) t (c :title)))

      ((repo :save) (t :id) e (c :version))
  )

  :rename_task (fn [c]
      ; (println "handlers:rename_task" c)
      (def t ((repo :get) (c :id)))
      (def e ((task :rename) t (c :title)))
      ((repo :save) (t :id) e (c :version))
  )

  :complete_task (fn [c]
      ; (println "handlers:complete_task" c)
      (def t ((repo :get) (c :id)))
      (def e ((task :complete) t))
      ((repo :save) (t :id) e (c :version))
  )
})

(defn get_id []
  (def res (client/post (str "http://jetdb.net/res")
      {
        :content-type :json
      }
    )
  )
  ((res :trace-redirects) 1)
)

(def db_id (get_id))
(def app {

  :send (fn [c]
    ; (println "app:send" c)
    (case (c :type)
       :createTask ((handlers :create_task) c)
       :renameTask ((handlers :rename_task) c)
       :completeTask ((handlers :complete_task) c)
    )
  )

  :handle (fn [e]
    ; (println e)
    (case (e :type)
       :taskCreated (
          (def t ((app :read) (e :id)))
          (def t (apply merge t {
            :version (e :version)
            :id (e :id)
            :title (e :title)
          } ))

          (client/patch db_id {
            :form-params t
            :content-type :json
          })
        )

       :taskRenamed (
          (def t ((app :read) (e :id)))
          (def t (apply merge t {
            :version (e :version)
            :title (e :title)
          } ))

          (client/patch db_id {
            :form-params t
            :content-type :json
          })
        )
       :taskCompleted (
          (def t ((app :read) (e :id)))
          (def t (apply merge t {
            :version (e :version)
            :completed (e :completed)
          } ))

          (client/patch db_id {
            :form-params t
            :content-type :json
          })

        )
    )

  )

  :read (fn [id]
    (def data ((client/get db_id {:as :json}) :body))
    (def data (if data data {}))
    (if (= (data :id) id) data {})
  )

})

(defn -main []

  (def id (get_id))

  (println "aaaaaaand ID is " id)

  ((eventbus :subscribe) (app :handle))

  ((app :send) {
    :id id,
    :title "new item"
    :type :createTask
    :version -1
  })

   (while (not (= (((app :read) id) :version) 0)) (do
         (println "waiting for v0")
     ))

  ((app :send) {
    :id id,
    :title "new item 1"
    :type :renameTask
    :version 0
  })

   (while (not (= (((app :read) id) :version) 1)) (do
        (println "waiting for v1")
    ))

  ((app :send) {
    :id id,
    :type :completeTask
    :version 1
  })

  (while (not (= (((app :read) id) :version) 2)) (do
       (println "waiting for v2")
   ))

  (def item ((app :read) id))
  (println item)
)
