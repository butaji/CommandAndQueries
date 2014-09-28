package handlers

import (
	"../domain"
	"../repository"
)

var repo = repository.Repository {}

func CreateTask(id string, version int, params interface{}) {
	task := domain.Task {}
	event := task.Create(id, params.(string))
	task.Apply(event)
	repo.Save(id, event, version)
}

func RenameTask(id string, version int, params interface{}) {
	task := repo.Get(id)
	event := task.Rename(params.(string))
	task.Apply(event)
	repo.Save(id, event, version)
}

func CompleteTask(id string, version int, params interface{}) {
	task := repo.Get(id)
	event := task.Complete()
	task.Apply(event)
	repo.Save(id, event, version)
}