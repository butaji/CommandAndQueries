//
//  Application.swift
//  CommandAndQueries
//
//  Created by Vitaly Baum on 11/10/14.
//  Copyright (c) 2014 Vitaly Baum. All rights reserved.
//

import Foundation

class Application {
    
    let handlers:CommandHandlers;
    
    init (handlers: CommandHandlers) {
        
        self.handlers = handlers

        EventBus.subscribe(self.taskCreated);
        EventBus.subscribe(self.taskRenamed);
        EventBus.subscribe(self.taskCompleted);
    }
    
    var items = Dictionary<String, TaskItem>()
    
    func taskCreated(e: TaskCreated) {
        items[e.id] = TaskItem(title: e.title, version: e.version)
    }
    
    func taskRenamed(e: TaskRenamed) {
        
        let i = read(e.id)
        
        items[e.id] = TaskItem(title: e.title, completed: i.completed, version: e.version)
    }
    
    func taskCompleted(e: TaskCompleted) {
        let i = read(e.id)
        
        items[e.id] = TaskItem(title: i.title, completed: true, version: e.version)
    }
    
    class func create() -> Application {
        
        return Application(handlers: CommandHandlers())
    }
    
    func send(cmd: CreateTask) {
        self.handlers.createTask(cmd);
    }
    
    func send(cmd: RenameTask) {
        self.handlers.renameTask(cmd);
    }
    
    func send(cmd: CompleteTask) {
        self.handlers.completeTask(cmd);
    }
    
    func read(id: String) -> TaskItem {
        let res = items[id] ?? TaskItem(title: "", version: -1)
        
        return res
    }
}
