package repository

import (
	"fmt"
	"../domain"
	"../eventstore"
	"../eventbus"
)

type Repository struct {
	
}

func (repo Repository) Get(id string) domain.Task {
    fmt.Println("get " + id)
	
	task := domain.Task {}
	
    events := eventstore.GetInstance().LoadHistoryFor(id);

    for _, e := range events {
        task.Apply(e)
    }
	
	return task
}

func (repo Repository) Save(id string, e domain.Event, v int) {
    fmt.Println("save " + id)
	
    eventstore.GetInstance().SaveEvent(id, &e, v)
    eventbus.Publish(e)
}