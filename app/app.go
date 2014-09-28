package app

import (
	"fmt"
	"../handlers"
	"../eventbus"
	"../domain"
)

const CreateTask = "CreateTask"
const RenameTask = "RenameTask"
const CompleteTask = "CompleteTask"

type TaskItem struct {
	Version int
	Title string
	Completed bool
}

type Command struct {
	Id string
	Target string
	Params interface{}
	Version int
}

type Application struct {
	Subscribe func(string, func(domain.Event))
	TaskItems map[string]TaskItem
}

func (app1 Application) Init() Application {
	app1.Subscribe = eventbus.Subscribe
	app1.TaskItems = map[string]TaskItem {}	
	
	app1.Subscribe(domain.TaskCreated, app1.taskCreated)
	app1.Subscribe(domain.TaskRenamed, app1.taskRenamed)
	app1.Subscribe(domain.TaskCompleted, app1.taskCompleted)
	
	return app1
}

func (app1 Application) taskCreated(e domain.Event) {
    app1.TaskItems[e.Id] = TaskItem{ Title: e.Params.(string), Version: e.Version }
	
    fmt.Printf("created %v\n", e.Id)
}

func (app1 Application) taskRenamed(e domain.Event) {
    
	task := app1.TaskItems[e.Id]
	
	task.Title = e.Params.(string)
	task.Version = e.Version
	
	app1.TaskItems[e.Id] = task
	
    fmt.Printf("renamed %v\n", e.Id)
}

func (app1 Application) taskCompleted(e domain.Event) {

	task := app1.TaskItems[e.Id]
	
	task.Completed = e.Params.(bool)
	task.Version = e.Version
	
	app1.TaskItems[e.Id] = task
	
    fmt.Printf("completed %v\n", e.Id)
}


func (app1 Application) Read(id string) TaskItem {
	// body
	
	task := app1.TaskItems[id]
    // fmt.Printf("read %v\n", task)
	
	return task
}

func (application Application) Send(cmd Command) {
	handler := getHandler(cmd.Target)
	handler(cmd.Id, cmd.Version, cmd.Params)
}

func getHandler(target string) func(string, int, interface{}) {
	
	if target == CreateTask {
		return handlers.CreateTask;
	}

	if target == RenameTask {
		return handlers.RenameTask;
	}

	if target == CompleteTask {
		return handlers.CompleteTask;
	}

	return nil;
}