package domain

import (
	"time"
)

const TaskCreated = "TaskCreated"
const TaskRenamed = "TaskRenamed"
const TaskCompleted = "TaskCompleted"

type Task struct {
	Id string
	Completed bool
}

type Event struct {
	Id string
	Target string
	Params interface{}
	Version int
	Time time.Time
}

func (task Task) Create(id string, title string) Event {
	return Event {
		Id: id,
		Target: TaskCreated,
		Params: title,
	}
}

func (task Task) Rename(title string) Event {
	return Event {
		Id: task.Id,
		Target: TaskRenamed,
		Params: title,
	}
}

func (task Task) Complete() Event {
	return Event {
		Id: task.Id,
		Target: TaskCompleted,
		Params: true,
	}
}

func (task *Task) Apply(e Event) {
	if e.Target == TaskCreated {
		task.Id = e.Id
	}
	
	if e.Target == TaskRenamed {
		
	}
	
	if e.Target == TaskCompleted {
		task.Completed = true
	}
	
}