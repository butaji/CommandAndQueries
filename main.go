package main

import (
    "fmt"
	"time"
	"./app"
    // "os/exec"
)

func main() {
	
	application := app.Application {}.Init()
	
//	id, _ := exec.Command("uuidgen").Output()

	id := "85ade304-462d-4321-8d1b-3db4377c6991"
	
	cmd1 := app.Command { 
			Id: id,
			Target: app.CreateTask,
			Params: "new title",
			Version: -1,
	}
	
	application.Send(cmd1)
	
	for application.Read(id).Version != 0 {
		fmt.Println("v0")
	}
	
	cmd2 := app.Command { 
			Id: id,
			Target: app.RenameTask,
			Params: "new title 1",
			Version: 0,
	}
	
	application.Send(cmd2)

	for application.Read(id).Version != 1 {
		 fmt.Println("v1")
		 time.Sleep(1 * time.Second)
	}
	
	cmd3 := app.Command { 
			Id: id,
			Target: app.CompleteTask,
			Version: 1,
	}
	
	application.Send(cmd3)
	
	for application.Read(id).Version != 2 {
		fmt.Println("v2")
		time.Sleep(1 * time.Second)
	}
	
	item := application.Read(id)
	
	fmt.Println(item.Title)
	fmt.Println(item.Completed)
}
